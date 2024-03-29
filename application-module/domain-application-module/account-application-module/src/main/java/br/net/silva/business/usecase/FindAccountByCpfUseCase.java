package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;

public class FindAccountByCpfUseCase implements UseCase<AccountOutput> {

    private final FindApplicationBaseGateway<AccountOutput> findActiveAccountRepository;
    private final GenericResponseMapper factory;

    private final IFactoryAggregate<Account, AccountDTO> factoryAggregate;

    public FindAccountByCpfUseCase(FindApplicationBaseGateway<AccountOutput> findActiveAccountRepository, GenericResponseMapper factory) {
        this.findActiveAccountRepository = findActiveAccountRepository;
        this.factory = factory;
        this.factoryAggregate = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var accountOutput = findActiveAccountRepository.findById((ICpfParam) param.input())
                .orElseThrow(() -> new AccountNotExistsException("Account not found"));

        var accountAggregate = factoryAggregate.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));
        factory.fillIn(accountAggregate.build(), param.output());
        return AccountBuilder.buildFullAccountOutput().createFrom(accountAggregate.build());
    }
}
