package com.mercadolivro.validation

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.service.BookService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

abstract class BookAvailableValidator(var bookService: BookService): ConstraintValidator<BookAvailable, String> {
    fun isBookAvailable(id: Int?): Boolean {
        return bookService.bookAvailable(id)
    }
}