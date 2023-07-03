package com.example.api_de_lancamentos.infrastructure.controller;

import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import com.example.api_de_lancamentos.domain.transaction.dto.TransactionRequestDTO;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final UseCase<List<Transaction>, List<Transaction>> registerPurchaseLaunchUseCase;

    @Autowired
    public TransactionController(UseCase<List<Transaction>, List<Transaction>> registerPurchaseLaunchUseCase) {
        this.registerPurchaseLaunchUseCase = registerPurchaseLaunchUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void postTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        registerPurchaseLaunchUseCase.execute(transactionRequestDTO.getTransactions());
    }
}
