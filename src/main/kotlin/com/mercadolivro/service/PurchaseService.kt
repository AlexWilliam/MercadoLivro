package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.events.PurchaseEvent
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.repository.PurchaseRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PurchaseService(
    private val purchaseRepository: PurchaseRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {

    fun create(purchaseModel: PurchaseModel){
        purchaseRepository.save(purchaseModel)
        applicationEventPublisher.publishEvent(PurchaseEvent(this, purchaseModel))
    }

    fun update(purchaseModel: PurchaseModel) {
        purchaseRepository.save(purchaseModel)
    }

    fun findBySeller(id: Int, pageable: Pageable): Page<PurchaseModel> {
        return purchaseRepository.findBySeller(id, pageable)
    }

    fun findByBuyer(id: Int, pageable: Pageable): Page<PurchaseModel> {
        return purchaseRepository.findByBuyer(id, pageable)
    }
}
