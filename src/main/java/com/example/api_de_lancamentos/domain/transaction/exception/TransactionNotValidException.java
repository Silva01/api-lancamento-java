package com.example.api_de_lancamentos.domain.transaction.exception;

public class TransactionNotValidException extends RuntimeException {
    public TransactionNotValidException(String message) {
        super(message);
    }
}
