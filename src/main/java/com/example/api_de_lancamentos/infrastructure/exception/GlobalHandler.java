package com.example.api_de_lancamentos.infrastructure.exception;

import com.example.api_de_lancamentos.domain.account.dto.AccountErrorDTO;
import com.example.api_de_lancamentos.domain.account.exception.AccountNotExistsException;
import com.example.api_de_lancamentos.domain.transaction.exception.TransactionNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(AccountNotExistsException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public AccountErrorDTO handleAccountNotExistsException(AccountNotExistsException anee) {
        return new AccountErrorDTO(anee.getMessage());
    }

    @ExceptionHandler(TransactionNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public AccountErrorDTO handleTransactionNotValidException(TransactionNotValidException tnve) {
        return new AccountErrorDTO(tnve.getMessage());
    }


}
