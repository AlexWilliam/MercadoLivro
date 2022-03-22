package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.mercadolivro.enums.BookStatus
import java.math.BigDecimal

class PutBookRequest (
    var id: Int? = null,
    var name: String?,
    var price: BigDecimal?,
    var status: BookStatus?,

    @JsonAlias("customer_id")
    var customerId: Int? = null

)
