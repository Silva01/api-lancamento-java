package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;

public class FindAccountUseCase implements UseCase<AccountOutput> {

    private final FindApplicationBaseGateway<AccountOutput> findAccountRepository;
    private final GenericResponseMapper factory;

    private final IFactoryAggregate<Account, AccountDTO> aggregateFactory;

    public FindAccountUseCase(FindApplicationBaseGateway<AccountOutput> findAccountRepository, GenericResponseMapper factory) {
        this.findAccountRepository = findAccountRepository;
        this.factory = factory;
        this.aggregateFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var accountOutput = findAccountRepository.findById((IAccountParam) param.input())
                .orElseThrow(() -> new AccountNotExistsException("Account not found"));

        var accountAggregate = aggregateFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));
        factory.fillIn(accountAggregate.build(), param.output());
        return AccountBuilder.buildFullAccountOutput().createFrom(accountAggregate.build());
    }
}
