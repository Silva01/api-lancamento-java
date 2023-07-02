package com.example.api_de_lancamentos.infrastructure.service;


import com.example.api_de_lancamentos.domain.account.repository.AccountRepository;
import com.example.api_de_lancamentos.infrastructure.model.Account;
import com.example.api_de_lancamentos.infrastructure.repository.AccountModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountRepository {

    private final AccountModelRepository repository;

    @Autowired
    public AccountService(AccountModelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account findAccountByAccountNumber(long accountNumber) {
        return repository.findById(accountNumber).orElse(null);
    }
}
