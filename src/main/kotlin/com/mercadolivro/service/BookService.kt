package com.mercadolivro.service

import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun getAll(): List<BookModel> {
        return bookRepository.findAll().toList()
    }

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun getById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow()
    }

    fun update(book: BookModel){
        if(!bookRepository.existsById(book.id!!)){
            throw Exception()
        }

        bookRepository.save(book)
    }

    fun delete(id: Int){
        if(!bookRepository.existsById(id!!)){
            throw Exception()
        }

        bookRepository.deleteById(id)
    }
}