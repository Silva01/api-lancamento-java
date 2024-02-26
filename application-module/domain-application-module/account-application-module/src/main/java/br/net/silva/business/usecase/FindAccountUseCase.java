package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IAccountParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class FindAccountUseCase implements UseCase<AccountOutput> {

    private final Repository<Optional<Account>> findAccountRepository;
    private final GenericResponseMapper factory;

    public FindAccountUseCase(Repository<Optional<Account>> findAccountRepository, GenericResponseMapper factory) {
        this.findAccountRepository = findAccountRepository;
        this.factory = factory;
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var findAccountDto = (IAccountParam) param.input();
        var accountOptional = findAccountRepository.exec(findAccountDto.accountNumber(), findAccountDto.agency());
        var accountAggregate =  accountOptional.orElseThrow(() -> new AccountNotExistsException("Account not found"));
        factory.fillIn(accountAggregate.build(), param.output());
        return AccountBuilder.buildFullAccountOutput().createFrom(accountAggregate.build());
    }
}
