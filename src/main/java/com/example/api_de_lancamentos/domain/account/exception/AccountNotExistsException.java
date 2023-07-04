package com.example.api_de_lancamentos.domain.account.exception;

public class AccountNotExistsException extends RuntimeException {

        public AccountNotExistsException(String message) {
            super(message);
        }
}
