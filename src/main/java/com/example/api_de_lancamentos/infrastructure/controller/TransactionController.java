package com.example.api_de_lancamentos.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postTransaction() {
        System.out.println("Isso é um teste ");
    }
}
