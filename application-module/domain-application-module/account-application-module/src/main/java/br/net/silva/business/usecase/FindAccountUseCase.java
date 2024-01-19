package br.net.silva.business.usecase;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.interfaces.IAccountParam;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.GenericResponseFactory;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class FindAccountUseCase implements UseCase<AccountDTO> {

    private final Repository<Optional<Account>> findAccountRepository;
    private final GenericResponseFactory factory;

    public FindAccountUseCase(Repository<Optional<Account>> findAccountRepository, GenericResponseFactory factory) {
        this.findAccountRepository = findAccountRepository;
        this.factory = factory;
    }

    @Override
    public AccountDTO exec(Source param) throws GenericException {
        var findAccountDto = (IAccountParam) param.input();
        var accountOptional = findAccountRepository.exec(findAccountDto.accountNumber(), findAccountDto.agency());
        var accountAggregate =  accountOptional.orElseThrow(() -> new AccountNotExistsException("Account not found"));
        factory.fillIn(accountAggregate.build(), param.output());
        return accountAggregate.build();
    }
}
