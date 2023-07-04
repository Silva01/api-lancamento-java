package com.example.api_de_lancamentos.infrastructure.repository.impl;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.exception.AccountNotExistsException;
import com.example.api_de_lancamentos.domain.account.factory.AccountFactory;
import com.example.api_de_lancamentos.domain.shared.interfaces.CreateRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.FindRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.UpdateRepository;
import com.example.api_de_lancamentos.infrastructure.model.AccountModel;
import com.example.api_de_lancamentos.infrastructure.repository.AccountModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountModelRepositoryIMPL implements FindRepository<Account, Long>, CreateRepository<Account>, UpdateRepository<Account> {

    private final AccountModelRepository repository;

    @Autowired
    public AccountModelRepositoryIMPL(AccountModelRepository repository) {
        this.repository = repository;
    }

    @Override
    public Account findBy(Long accountNumber) {
        Optional<AccountModel> optionalAccountModel = repository.findById(accountNumber);
        if (optionalAccountModel.isEmpty()) {
            throw new AccountNotExistsException("Conta não encontrada");
        }

        AccountModel accountModel = optionalAccountModel.get();
        return createAccount(accountModel);
    }

    @Override
    public Account create(Account entity) {
        AccountModel accountModel = new AccountModel();
        accountModel.setName(entity.getAccountName());
        accountModel.setBalance(entity.getAccountBalance());
        accountModel.setCreditBalance(entity.getAccountCreditBalance());
        accountModel.setAccountNumber(entity.getAccountNumber());

        AccountModel accountModelSaved = repository.save(accountModel);
        return createAccount(accountModelSaved);
    }

    private Account createAccount(AccountModel accountModel) {
        return AccountFactory.createAccount(accountModel.getAccountNumber(), accountModel.getName(), accountModel.getBalance(), accountModel.getCreditBalance());
    }

    @Override
    public Account update(Account entity) {
        Optional<AccountModel> accountModel = repository.findById(entity.getAccountNumber());

        if (accountModel.isEmpty()) {
            throw new AccountNotExistsException("Conta não encontrada");
        }

        AccountModel accountModelSaved = accountModel.get();
        accountModelSaved.setBalance(entity.getAccountBalance());
        accountModelSaved.setCreditBalance(entity.getAccountCreditBalance());
        accountModelSaved.setName(entity.getAccountName());
        repository.save(accountModelSaved);

        return entity;
    }
}
