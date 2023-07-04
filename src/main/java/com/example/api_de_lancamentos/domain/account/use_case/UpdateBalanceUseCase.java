package com.example.api_de_lancamentos.domain.account.use_case;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.shared.interfaces.UpdateRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;

public class UpdateBalanceUseCase implements UseCase<Boolean, Account> {

    private final UpdateRepository<Account> updateRepository;

    public UpdateBalanceUseCase(UpdateRepository<Account> updateRepository) {
        this.updateRepository = updateRepository;
    }

    @Override
    public Boolean execute(Account entity) {
        updateRepository.update(entity);
        return Boolean.TRUE;
    }
}
