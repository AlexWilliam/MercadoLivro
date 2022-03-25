package com.mercadolivro.model

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.BadRequestException
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name="purchase")
data class PurchaseModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(name="customer_id")
    var customer: CustomerModel,

    @Column
    var nfe: String? = null,

    @Column
    var price: BigDecimal,

    @Column
    val createdAt: LocalDateTime = LocalDateTime.now()
){

    @ManyToMany
    @JoinTable(name="purchase_book",
        joinColumns = [JoinColumn(name="purchase_id")],
        inverseJoinColumns = [JoinColumn(name="book_id")])
    var books: MutableList<BookModel>? = null
        set(value){
            value!!.map {
                if(it.status == BookStatus.VENDIDO){
                    throw BadRequestException(Errors.ML301.message.format(it.id), Errors.ML301.code)
                }
                if(it.status == BookStatus.CANCELADO || it.status == BookStatus.DELETADO){
                    throw BadRequestException(Errors.ML302.message.format(it.status), Errors.ML301.code)
                }
            }
            field = value
        }
    constructor(
        id: Int? = null,
        customer: CustomerModel,
        books: MutableList<BookModel>?,
        nfe: String? = null,
        price: BigDecimal
    ): this(id, customer, nfe, price){
        this.books = books
    }
}