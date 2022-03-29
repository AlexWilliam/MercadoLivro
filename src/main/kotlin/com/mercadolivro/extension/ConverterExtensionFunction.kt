package com.mercadolivro.extension

import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.controller.response.PurchaseResponse
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.PurchaseModel
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService

fun PostCustomerRequest.toCustomerModel(): CustomerModel{
    return CustomerModel(
        name=this.name,
        email=this.email,
        status = CustomerStatus.ATIVO,
        password = this.password)
}

fun PutCustomerRequest.toCustomerModel(previousValue: CustomerModel): CustomerModel{
    return CustomerModel(
        id=previousValue.id,
        name=this.name ?: previousValue.name,
        email=this.email ?: previousValue.email,
        status=CustomerStatus.ATIVO ?: previousValue.status,
        password=previousValue.password
    )
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(name=this.name, price=this.price, status=BookStatus.ATIVO, customer=customer)
}

fun PutBookRequest.toBookModel(previousValue: BookModel, customer: CustomerModel): BookModel{
    return BookModel(
        id=previousValue.id,
        name=this.name ?: previousValue.name,
        price=this.price ?: previousValue.price,
        status=BookStatus.ATIVO ?: previousValue.status,
        customer=customer ?: previousValue.customer)
}

fun CustomerModel.toResponse(): CustomerResponse {
    return CustomerResponse(
        id=this.id,
        name=this.name,
        email=this.email,
        status=this.status
    )
}

fun BookModel.toResponse(): BookResponse {
    return BookResponse(
        id=this.id,
        name=this.name,
        price=this.price,
        customer=this.customer,
        status=this.status
    )
}

fun PurchaseModel.toResponse(): PurchaseResponse {
    return PurchaseResponse(
        id=this.id,
        seller=this.seller,
        buyer=this.buyer,
        books=this.books,
        nfe=this.nfe,
        price=this.price,
        createdAt=this.createdAt
    )
}