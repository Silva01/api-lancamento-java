package com.example.api_de_lancamentos.infrastructure.controller;

import com.example.api_de_lancamentos.domain.transaction.value_object.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postTransaction(@RequestBody TransactionDTO transactionDTO) {
        System.out.println("Isso é um teste " + transactionDTO.name);
    }
}
