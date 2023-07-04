package com.example.api_de_lancamentos.infrastructure.controller;


import com.example.api_de_lancamentos.domain.account.dto.BalanceDTO;
import com.example.api_de_lancamentos.domain.account.dto.CreateAccountDTO;
import com.example.api_de_lancamentos.domain.account.dto.CreatedAccountNumberDTO;
import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.factory.AccountFactory;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final UseCase<BalanceDTO, Long> getBalanceAccountUseCase;
    private final UseCase<CreatedAccountNumberDTO, Account> createAccountUseCase;

    @Autowired
    public AccountController(UseCase<BalanceDTO, Long> getBalanceAccountUseCase, UseCase<CreatedAccountNumberDTO, Account> createAccountUseCase) {
        this.getBalanceAccountUseCase = getBalanceAccountUseCase;
        this.createAccountUseCase = createAccountUseCase;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreatedAccountNumberDTO createAccount(@RequestBody CreateAccountDTO createAccountDTO) {
        return createAccountUseCase.execute(AccountFactory.createAccount(createAccountDTO.getName()));
    }

    @GetMapping(value = "/balance/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public BalanceDTO getBalance(@PathVariable("accountNumber") Long accountNumber) {
        return getBalanceAccountUseCase.execute(accountNumber);
    }
}
