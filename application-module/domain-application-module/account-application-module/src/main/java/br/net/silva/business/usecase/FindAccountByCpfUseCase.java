package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.application.value_object.Source;

import java.util.Optional;

public class FindAccountByCpfUseCase implements UseCase<AccountOutput> {

    private final Repository<Optional<AccountOutput>> findActiveAccountRepository;
    private final GenericResponseMapper factory;

    private final IFactoryAggregate<Account, AccountDTO> factoryAggregate;

    public FindAccountByCpfUseCase(Repository<Optional<AccountOutput>> findActiveAccountRepository, GenericResponseMapper factory) {
        this.findActiveAccountRepository = findActiveAccountRepository;
        this.factory = factory;
        this.factoryAggregate = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var findAccountDto = (IAccountParam) param.input();
        var accountOptional = findActiveAccountRepository.exec(findAccountDto.cpf());
        var accountOutput =  accountOptional.orElseThrow(() -> new AccountNotExistsException("Account not found"));
        var accountAggregate = factoryAggregate.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));
        factory.fillIn(accountAggregate, param.output());
        return AccountBuilder.buildFullAccountOutput().createFrom(accountAggregate.build());
    }
}
