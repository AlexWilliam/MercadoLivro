package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.mercadolivro.model.BookModel
import java.math.BigDecimal
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class PostBookRequest (
    var id: Int? = null,

    @field:NotEmpty(message="Nome deve ser preenchido!")
    var name: String,

    @field:NotNull(message="Preço deve ser preenchido!")
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int? = null

        )