package com.mercadolivro.controller.request

import com.mercadolivro.enums.CustomerStatus

data class PutCustomerRequest(
    var name: String?,
    var email: String?,
    var status: CustomerStatus?
    )