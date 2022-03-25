package com.mercadolivro.controller

import com.mercadolivro.controller.mapper.PurchaseMapper
import com.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.service.BookService
import com.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/purchases")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper,
    private val bookService: BookService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody request: PostPurchaseRequest){
        val purchase = purchaseMapper.toModel(request)
        purchase.books!!.map{
            bookService.bookAvailable(it.id, it.status)
        }
        purchaseService.create(purchase)
    }
}