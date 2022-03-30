package com.mercadolivro.controller.response

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin")
class AdminController () {

    @GetMapping("/reports")
    fun getAll(): String {
        return "This is a report, only admin can see!"
    }
}