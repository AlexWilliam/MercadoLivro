package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.enums.Roles
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.extension.toPageResponse
import com.mercadolivro.helper.buildCustomer
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import net.bytebuddy.matcher.ElementMatchers.any
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*
import kotlin.random.Random.Default.nextInt

@ExtendWith(MockKExtension::class)
class CustomerServiceTest{

    @MockK
    private lateinit var customerRepository: CustomerRepository

    @MockK
    private lateinit var bookService: BookService

    @MockK
    private lateinit var bCrypt: BCryptPasswordEncoder

    @InjectMockKs
    @SpyK
    private lateinit var customerService: CustomerService

    @Test
    fun `should return all customers`(){

        val fakeCustomers = listOf(
            buildCustomer(),
            buildCustomer()
        )

        val pageFakeCustomers = PageImpl<CustomerModel>(fakeCustomers.subList(0, 2))

        every { customerRepository.findAll(Pageable.ofSize(2).withPage(0)) } returns pageFakeCustomers

        val customers = customerService.getAll(null, Pageable.ofSize(2).withPage(0))

        assertEquals(pageFakeCustomers, customers)
        verify(exactly = 1){ customerRepository.findAll(Pageable.ofSize(2).withPage(0)) }
        verify(exactly = 0) { customerRepository.findByNameContaining(any(), Pageable.ofSize(2).withPage(0)) }
    }

    @Test
    fun `should return all customers with name`(){

        val name = "Alex"

        val fakeCustomers = listOf(
            buildCustomer()
        )

        val pageFakeCustomers = PageImpl<CustomerModel>(fakeCustomers.subList(0, 1))

        every { customerRepository.findByNameContaining(name, Pageable.ofSize(1).withPage(0)) } returns pageFakeCustomers

        val customers = customerService.getAll(name, Pageable.ofSize(1).withPage(0))

        assertEquals(pageFakeCustomers, customers)
        verify(exactly = 0){ customerRepository.findAll(Pageable.ofSize(1).withPage(0)) }
        verify(exactly = 1) { customerRepository.findByNameContaining(name, Pageable.ofSize(1).withPage(0)) }
    }

    @Test
    fun `should create customer and encrypt password`(){

        val initialPassword = Math.random().toString()

        val fakeCustomer = buildCustomer(password = initialPassword)
        val fakePassword = UUID.randomUUID().toString()
        val fakeCustomerEncryptedPassword = fakeCustomer.copy(password = fakePassword)

        every { customerRepository.save(fakeCustomerEncryptedPassword) } returns fakeCustomer
        every { bCrypt.encode(initialPassword) } returns fakePassword

        customerService.create(fakeCustomer)

        verify(exactly = 1){ customerRepository.save(any()) }
        verify(exactly = 1){ bCrypt.encode(initialPassword) }
    }

    @Test
    fun `should return customer by id`(){

        val id = Random().nextInt()

        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.findById(any()) } returns Optional.of(fakeCustomer)

        val customer = customerService.findById(id)

        assertEquals(fakeCustomer, customer)
        verify(exactly = 1){ customerRepository.findById(id) }

    }

    @Test
    fun `should throw not found exception customer find by id`(){
        val id = Random().nextInt()

        every { customerRepository.findById(id) } returns Optional.empty()

        val errors = assertThrows<NotFoundException>{ customerService.findById(id) }

        assertEquals("Customer [${id}] not exists!", errors.message)
        assertEquals("ML-201", errors.errorCode)
        verify(exactly = 1){ customerRepository.findById(id) }
    }

    @Test
    fun `should update customer`(){
        val id = Random().nextInt()

        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns true
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        customerService.update(fakeCustomer)

        verify(exactly = 1){ customerRepository.existsById(id) }
        verify(exactly = 1){ customerRepository.save(fakeCustomer) }
    }

    @Test
    fun `should throw error when update customer not found`(){
        val id = Random().nextInt()

        val fakeCustomer = buildCustomer(id = id)

        every { customerRepository.existsById(id) } returns false
        every { customerRepository.save(fakeCustomer) } returns fakeCustomer

        val errors = assertThrows<NotFoundException>{
            customerService.update(fakeCustomer)
        }

        assertEquals("Customer [${id}] not exists!", errors.message)
        assertEquals("ML-201", errors.errorCode)
        verify(exactly = 1){ customerRepository.existsById(id) }
        verify(exactly = 0){ customerRepository.save(any()) }
    }

    @Test
    fun `should delete customer`(){
        val id = Random().nextInt()
        val fakeCustomer = buildCustomer(id = id)
        val expectedCustomer = fakeCustomer.copy(status = CustomerStatus.INATIVO)

        every { customerService.findById(id) } returns fakeCustomer
        every { bookService.deleteByCustomer(fakeCustomer) } just runs
        every { customerRepository.save(expectedCustomer)} returns expectedCustomer

        customerService.delete(id)

        verify(exactly = 1){ bookService.deleteByCustomer(fakeCustomer) }
        verify(exactly = 1){ customerRepository.save(expectedCustomer) }
    }

    @Test
    fun `should throw error when delete customer not found`(){
        val id = Random().nextInt()

        every { customerService.findById(id) } throws NotFoundException(Errors.ML201.message.format(id), Errors.ML201.code)

        val errors = assertThrows<NotFoundException> {
            customerService.delete(id)
        }

        assertEquals("Customer [${id}] not exists!", errors.message)
        assertEquals("ML-201", errors.errorCode)
        verify(exactly = 1){ customerService.findById(id) }
        verify(exactly = 0){ bookService.deleteByCustomer(any()) }
        verify(exactly = 0){ customerRepository.save(any()) }
    }

    @Test
    fun `should return true when email available`(){
        val email = "${Random().nextInt().toString()}@email.com"

        every { customerRepository.existsByEmail(email) } returns false

        val emailAvailable = customerService.emailAvailable(email)

        assertTrue(emailAvailable)
        verify(exactly = 1){ customerRepository.existsByEmail(email) }
    }

    @Test
    fun `should return false when email available`(){
        val email = "${Random().nextInt().toString()}@email.com"

        every { customerRepository.existsByEmail(email) } returns true

        val emailAvailable = customerService.emailAvailable(email)

        assertFalse(emailAvailable)
        verify(exactly = 1){ customerRepository.existsByEmail(email) }
    }
}