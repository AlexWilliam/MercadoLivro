package com.mercadolivro.controller.response

import com.mercadolivro.model.CustomerModel
import java.math.BigDecimal
import java.time.LocalDateTime

data class PurchaseResponse(
    var id: Int? = null,
    var seller: CustomerModel,
    var buyer: CustomerModel,
    var nfe: String? = null,
    var price: BigDecimal,
    val createdAt: LocalDateTime
) {

}