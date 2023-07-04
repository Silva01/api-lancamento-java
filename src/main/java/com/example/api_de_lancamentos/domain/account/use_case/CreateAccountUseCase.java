package com.example.api_de_lancamentos.domain.account.use_case;

import com.example.api_de_lancamentos.domain.account.dto.CreatedAccountNumberDTO;
import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.shared.interfaces.CreateRepository;
import com.example.api_de_lancamentos.domain.shared.interfaces.UseCase;

public final class CreateAccountUseCase implements UseCase<CreatedAccountNumberDTO, Account> {

    private final CreateRepository<Account> createRepository;

    public CreateAccountUseCase(CreateRepository<Account> createRepository) {
        this.createRepository = createRepository;
    }

    @Override
    public CreatedAccountNumberDTO execute(Account entity) {
        return new CreatedAccountNumberDTO(createRepository.create(entity).getAccountNumber());
    }
}
