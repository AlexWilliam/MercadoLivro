package com.mercadolivro.helper

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Roles
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.model.PurchaseModel
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

fun buildCustomer(
    id: Int? = null,
    name: String = "customer_name",
    email: String = "${UUID.randomUUID()}@email.com",
    password: String = "password"
) = CustomerModel(
    id = id,
    name = name,
    email = email,
    status = CustomerStatus.ATIVO,
    password = password,
    roles = setOf(Roles.CUSTOMER)
)

fun buildBook(
    id: Int? = null,
    name: String = "Livro",
    price: BigDecimal = BigDecimal.TEN,
    customer: CustomerModel? = buildCustomer(),
    status: BookStatus = BookStatus.ATIVO
) = BookModel(
    id = id,
    name = name,
    price = price,
    customer = customer,
    status = status
)

fun buildPurchase(
    id: Int? = null,
    seller: CustomerModel = buildCustomer(),
    buyer: CustomerModel = buildCustomer(),
    books: MutableList<BookModel> = mutableListOf<BookModel>(),
    nfe: String? = UUID.randomUUID().toString(),
    price: BigDecimal = BigDecimal.TEN
) = PurchaseModel(
    id = id,
    seller = seller,
    buyer = buyer,
    books = books,
    nfe = nfe,
    price = price
)
