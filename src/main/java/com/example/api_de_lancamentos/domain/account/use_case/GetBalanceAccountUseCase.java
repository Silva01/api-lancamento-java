package com.example.api_de_lancamentos.domain.account.use_case;

import com.example.api_de_lancamentos.domain.account.dto.BalanceDTO;
import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.account.exception.AccountNotExistsException;
import com.example.api_de_lancamentos.domain.shared.interfaces.FindRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;

public final class GetBalanceAccountUseCase implements UseCase<BalanceDTO, Long> {

    private final FindRepository<Account, Long> findRepository;

    public GetBalanceAccountUseCase(FindRepository<Account, Long> findRepository) {
        this.findRepository = findRepository;
    }

    @Override
    public BalanceDTO execute(Long accountNumber) throws AccountNotExistsException {
        Account account = findRepository.findBy(accountNumber);
        return new BalanceDTO(account.getAccountBalance(), account.getAccountCreditBalance());
    }
}
