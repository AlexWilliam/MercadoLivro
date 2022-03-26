package com.mercadolivro.controller

import com.mercadolivro.controller.mapper.PurchaseMapper
import com.mercadolivro.controller.request.PostPurchaseRequest
import com.mercadolivro.controller.response.PurchaseResponse
import com.mercadolivro.extension.toResponse
import com.mercadolivro.service.BookService
import com.mercadolivro.service.PurchaseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
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
            bookService.bookAvailable(it.id)
        }
        purchaseService.create(purchase)
    }

    @GetMapping("/soldByCustomer")
    fun findBySeller(@PathVariable id: Int, @PageableDefault(page=0, size=10) pageable: Pageable): Page<PurchaseResponse> {
        return purchaseService.findBySeller(id, pageable).map{ it.toResponse() }
    }

    @GetMapping("/broughtByCustomer")
    fun findByBuyer(@PageableDefault(page=0, size=10) pageable: Pageable, @PathVariable id: Int): Page<PurchaseResponse> {
        return purchaseService.findByBuyer(id, pageable).map{ it.toResponse() }
    }
}