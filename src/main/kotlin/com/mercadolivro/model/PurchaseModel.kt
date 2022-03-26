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
    @JoinColumn(name="seller")
    var seller: CustomerModel,

    @ManyToOne
    @JoinColumn(name="buyer")
    var buyer: CustomerModel,

    @ManyToMany
    @JoinTable(name="purchase_book",
        joinColumns = [JoinColumn(name="purchase_id")],
        inverseJoinColumns = [JoinColumn(name="book_id")])
    var books: MutableList<BookModel>,

    @Column
    var nfe: String? = null,

    @Column
    var price: BigDecimal,

    @Column
    val createdAt: LocalDateTime = LocalDateTime.now()
){
}