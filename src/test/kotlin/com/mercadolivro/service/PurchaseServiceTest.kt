package com.mercadolivro.service

import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.helper.buildCustomer
import com.mercadolivro.helper.buildPurchase
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import java.util.*
import javax.persistence.*
import javax.swing.text.html.Option

@ExtendWith(MockKExtension::class)
class PurchaseServiceTest {

    @InjectMockKs
    private lateinit var purchaseService: PurchaseService

    @MockK
    private lateinit var purchaseRepository: PurchaseRepository

    @MockK
    private lateinit var applicationEventPublisher: ApplicationEventPublisher

    private val purchaseEventSlot = slot<PurchaseEvent>()

    @Test
    fun `should create purchase and publish event`() {
        val purchase = buildPurchase()

        every { purchaseRepository.save(purchase) } returns purchase
        every { applicationEventPublisher.publishEvent(any()) } just runs

        purchaseService.create(purchase)

        verify(exactly = 1) { purchaseRepository.save(purchase) }
        verify(exactly = 1) { applicationEventPublisher.publishEvent(capture(purchaseEventSlot)) }

        assertEquals(purchase, purchaseEventSlot.captured.purchaseModel)
    }

    @Test
    fun `should update purchase`(){
        val purchase = buildPurchase()

        every { purchaseRepository.save(purchase) } returns purchase

        purchaseService.update(purchase)

        verify(exactly = 1) { purchaseRepository.save(purchase) }
    }

    @Test
    fun `should return all purchases pageable`(){

        val fakePurchases = listOf(
            buildPurchase(),
            buildPurchase()
        )
        val pageFakeCustomers = PageImpl<PurchaseModel>(fakePurchases.subList(0, 2))
        val pageable = Pageable.ofSize(10).withPage(0)

        every { purchaseRepository.findAll(pageable) } returns pageFakeCustomers

        val purchases = purchaseService.findAll(Pageable.ofSize(10).withPage(0))

        assertEquals(pageFakeCustomers, purchases)
        verify(exactly = 1){ purchaseRepository.findAll(Pageable.ofSize(10).withPage(0)) }
    }

    @Test
    fun `should return all purchases by seller pageable`(){

        val fakeCustomer = buildCustomer()

        val fakePurchases = listOf(
            buildPurchase(),
            buildPurchase()
        )
        val pageable = Pageable.ofSize(2).withPage(0)

        val pageFakePurchases = PageImpl<PurchaseModel>(fakePurchases.subList(0, 2))


        every { purchaseRepository.findBySeller(fakeCustomer, pageable) } returns pageFakePurchases

        val purchases = purchaseService.findBySeller(fakeCustomer, pageable)

        assertEquals(pageFakePurchases, purchases)
        verify(exactly = 1){ purchaseRepository.findBySeller(fakeCustomer, Pageable.ofSize(2).withPage(0)) }
    }

    @Test
    fun `should return all purchases by buyer pageable`(){

        val fakeCustomer = buildCustomer()

        val fakePurchases = listOf(
            buildPurchase()
        )
        val pageable = Pageable.ofSize(1).withPage(0)

        val pageFakePurchases = PageImpl<PurchaseModel>(fakePurchases.subList(0, 1))

        every { purchaseRepository.findByBuyer(fakeCustomer, pageable) } returns pageFakePurchases

        val purchases = purchaseService.findByBuyer(fakeCustomer, pageable)

        assertEquals(pageFakePurchases, purchases)
        verify(exactly = 1){ purchaseRepository.findByBuyer(fakeCustomer, Pageable.ofSize(1).withPage(0)) }
    }

}