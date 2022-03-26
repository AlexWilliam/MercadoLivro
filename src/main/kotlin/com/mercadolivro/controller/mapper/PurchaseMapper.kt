package com.mercadolivro.controller.mapper

import com.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    fun toModel(request: PostPurchaseRequest): PurchaseModel{
        val seller = customerService.findById(request.seller)
        val buyer = customerService.findById(request.buyer)
        val books = bookService.findAllByIds(request.bookIds)

        return PurchaseModel(
            seller = seller,
            buyer = buyer,
            books = books.toMutableList(),
            price = books.sumOf { it.price }
        )
    }
}