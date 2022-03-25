package com.mercadolivro.validation

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.service.BookService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

abstract class BookAvailableValidator(var bookService: BookService): ConstraintValidator<BookAvailable, String> {
    fun isBookAvailable(id: Int?, status: BookStatus): Boolean {
        if(status.toString().isNullOrEmpty())
            return false

        return bookService.bookAvailable(id, status)
    }
}