package com.example.api_de_lancamentos.domain.account.repository;

import com.example.api_de_lancamentos.infrastructure.model.Account;

public interface AccountRepository {
    Account findAccountByAccountNumber(long accountNumber);
}
