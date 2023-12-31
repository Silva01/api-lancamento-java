package com.example.api_de_lancamentos.domain.shared.interfaces;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.exception.AccountNotExistsException;

public interface FindRepository<R,P> {
    R findBy(P param);
}
