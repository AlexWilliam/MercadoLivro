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
    @JoinColumn(name="seller", updatable=false)
    var seller: CustomerModel,

    @ManyToMany
    @JoinTable(name="purchase_book",
        joinColumns = [JoinColumn(name="purchase_id")],
        inverseJoinColumns = [JoinColumn(name="book_id")])
    var books: MutableList<BookModel>,

    @ManyToOne
    @JoinColumn(name="buyer")
    var buyer: CustomerModel,

    @Column
    var nfe: String? = null,

    @Column
    var price: BigDecimal,

    @Column
    val createdAt: LocalDateTime = LocalDateTime.now()
){
//
//    ? = null
//        set(value){
//            if(field!!.any { it.status == BookStatus.VENDIDO })
//                throw BadRequestException(Errors.ML301.message, Errors.ML301.code)
//
//            if(field!!.any { it.status == BookStatus.CANCELADO || it.status == BookStatus.DELETADO})
//                throw BadRequestException(Errors.ML302.message.format(BookStatus.CANCELADO, BookStatus.DELETADO), Errors.ML302.code)
//
//            field!!.add(value)
//            println(field)
//            println(value)
//        }
//    constructor(
//        id: Int? = null,
//        seller: CustomerModel,
//        buyer: CustomerModel,
//        nfe: String? = null,
//        price: BigDecimal,
//        books: MutableList<BookModel>?
//    ): this(id, seller, buyer, nfe, price){
//        this.books = books
//        println(this)
//    }
}