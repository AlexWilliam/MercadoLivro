package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.Errors
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun findAll(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    fun findActives(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
    }

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow{ NotFoundException(Errors.ML101.message.format(id), Errors.ML101.code) }
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

    fun deleteByCustomer(customer: CustomerModel) {
        var books = bookRepository.findByCustomer(customer)

        for(book in books){
            book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }

    fun findAllByIds(bookIds: Set<Int>): List<BookModel> {
        return bookRepository.findAllById(bookIds).toList()
    }

    fun purchase(books: MutableList<BookModel>?) {
        books!!.map{
            it.status = BookStatus.VENDIDO
        }
        bookRepository.saveAll(books)
    }

    fun bookAvailable(id: Int?): Boolean {
        if(bookRepository.existsByIdAndStatus(id, BookStatus.VENDIDO))
            throw BadRequestException(Errors.ML301.message.format(id), Errors.ML301.code)

        if(bookRepository.existsByIdAndStatus(id, BookStatus.CANCELADO))
            throw BadRequestException(Errors.ML302.message.format(BookStatus.CANCELADO), Errors.ML302.code)

        if(bookRepository.existsByIdAndStatus(id, BookStatus.DELETADO))
            throw BadRequestException(Errors.ML302.message.format(BookStatus.DELETADO), Errors.ML302.code)

        return !bookRepository.existsByIdAndStatus(id, BookStatus.ATIVO)
    }
}