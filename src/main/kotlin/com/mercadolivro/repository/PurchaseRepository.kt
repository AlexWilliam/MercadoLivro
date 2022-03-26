package com.mercadolivro.repository

import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.PurchaseModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface PurchaseRepository: JpaRepository<PurchaseModel, Int> {
    fun findBySeller(seller: CustomerModel, pageable: Pageable) : Page<PurchaseModel>
    fun findByBuyer(buyer: CustomerModel, pageable: Pageable) : Page<PurchaseModel>
}
