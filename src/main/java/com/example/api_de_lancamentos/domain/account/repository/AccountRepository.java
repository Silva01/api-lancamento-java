package com.example.api_de_lancamentos.domain.account.repository;

import com.example.api_de_lancamentos.domain.account.aggregate.Account;

public interface AccountRepository {
    Account findAccountByAccountNumber(long accountNumber);
}
