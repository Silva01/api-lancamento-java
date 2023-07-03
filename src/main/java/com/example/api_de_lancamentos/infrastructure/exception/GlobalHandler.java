package com.example.api_de_lancamentos.infrastructure.exception;

import com.example.api_de_lancamentos.domain.account.dto.AccountErrorDTO;
import com.example.api_de_lancamentos.domain.account.exception.AccountNotExistsException;
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

}
