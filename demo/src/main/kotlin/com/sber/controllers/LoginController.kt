package com.sber.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import javax.websocket.server.PathParam

@Controller
@RequestMapping("/login")
class LoginController {

    @GetMapping
    fun getLoginPage(): String {
        return "loginPage"
    }

    @PostMapping
    fun enterLogin(
        @RequestParam("username") login: String,
        @RequestParam("password") password: String
    ): String {
        return "redirect:/app/list"
    }
}