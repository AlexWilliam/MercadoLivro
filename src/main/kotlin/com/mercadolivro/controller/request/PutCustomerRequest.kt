package com.mercadolivro.controller.request

import com.mercadolivro.enums.CustomerStatus
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class PutCustomerRequest(

    @field:NotEmpty(message="Nome deve ser preenchido!")
    var name: String,

    @field:Email(message="E-mail deve ser válido!")
    var email: String,

    var status: CustomerStatus?
    )