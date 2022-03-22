package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun findAll(): List<BookModel> {
        return bookRepository.findAll().toList()
    }

    fun findActives(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow()
    }

    fun update(book: BookModel){
        if(!bookRepository.existsById(book.id!!)){
            throw Exception()
        }

        bookRepository.save(book)
    }

    fun delete(id: Int){
        var book = findById(id)

        book.status = BookStatus.CANCELADO

        if(!bookRepository.existsById(id!!)){
            throw Exception()
        }

        bookRepository.save(book)
    }
}