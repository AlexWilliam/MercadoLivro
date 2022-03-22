package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal

class PutBookRequest (
    var id: Int? = null,
    var name: String,
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int? = null

)
