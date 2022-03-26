package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import com.mercadolivro.validation.BookAvailable
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive

class PostPurchaseRequest(

    @field:NotNull
    @field:Positive
    val seller: Int,

    @field:NotNull
    @field:Positive
    val buyer: Int,

    @field:NotNull
    @JsonAlias("book_ids")
    @BookAvailable
    val bookIds: Set<Int>
)