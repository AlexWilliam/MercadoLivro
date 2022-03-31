package com.mercadolivro.repository

import com.mercadolivro.helper.buildCustomer
import com.mercadolivro.model.CustomerModel
import io.mockk.every
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerRepositoryTest {

    @Autowired
    private lateinit var customerRepository: CustomerRepository

    @BeforeEach
    fun setup() = customerRepository.deleteAll()

    @Test
    fun `should return name containing`(){
        val listCustomers = listOf(
            customerRepository.save(buildCustomer(name="William")),
            customerRepository.save(buildCustomer(name="Amanda")),
            customerRepository.save(buildCustomer(name="Vinicius"))
        )

        val fakePageCustomers = PageImpl<CustomerModel>(listCustomers.subList(0, 2))

        val pageable = Pageable.ofSize(2).withPage(0)

        val customers = customerRepository.findByNameContaining("am", pageable)

        assertEquals(fakePageCustomers.content, customers.content)
    }

    @Nested
    inner class FindByEmail{
        @Test
        fun `should return customer when email exists`(){
            val email = "teste@teste.com"
            val customer = customerRepository.save(buildCustomer(email=email))

            val result = customerRepository.findByEmail(customer.email)

            assertNotNull(result)
            assertEquals(customer, result)
        }

        @Test
        fun `should return null when email non-exists`(){
            val email = "nonexistingemailtest@teste.com"

            val result = customerRepository.existsByEmail(email)

            assertNull(result)
        }
    }

    @Nested
    inner class ExistsByEmail{
        @Test
        fun `should return true when email exists`(){
            val email = "teste@teste.com"
            customerRepository.save(buildCustomer(email=email))

            val exists = customerRepository.existsByEmail(email)

            assertTrue(exists)
        }

        @Test
        fun `should return false when email exists`(){
            val email = "nonexistingemailtest@teste.com"

            val exists = customerRepository.existsByEmail(email)

            assertFalse(exists)
        }
    }
}