package com.mercadolivro.controller.response

import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import java.math.BigDecimal
import java.time.LocalDateTime

data class PurchaseResponse(
    var id: Int? = null,
    var seller: CustomerModel? = null,
    var buyer: CustomerModel? = null,
    var books: MutableList<BookModel>? = null,
    var nfe: String? = null,
    var price: BigDecimal,
    val createdAt: LocalDateTime
) {

}