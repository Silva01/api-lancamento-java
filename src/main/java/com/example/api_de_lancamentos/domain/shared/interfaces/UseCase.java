package com.example.api_de_lancamentos.domain.shared.interfaces;

import com.example.api_de_lancamentos.domain.account.exception.AccountNotExistsException;

public interface UseCase<R,E> {
    R execute(E entity) throws AccountNotExistsException;
}
