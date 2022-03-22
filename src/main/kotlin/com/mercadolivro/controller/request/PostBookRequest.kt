package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.mercadolivro.model.BookModel
import java.math.BigDecimal

class PostBookRequest (
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int? = null

        )