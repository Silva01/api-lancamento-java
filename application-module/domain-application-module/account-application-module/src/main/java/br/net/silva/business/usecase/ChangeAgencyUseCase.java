package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.application.value_object.Source;

public class ChangeAgencyUseCase implements UseCase<EmptyOutput> {

    private final ApplicationBaseGateway<AccountOutput> baseGateway;

    private final IFactoryAggregate<Account, AccountDTO> factoryAggregate;

    public ChangeAgencyUseCase(ApplicationBaseGateway<AccountOutput> baseGateway) {
        this.baseGateway = baseGateway;
        this.factoryAggregate = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        try {
            var changeAgencyInput = (ChangeAgencyInput) param.input();
            var accountOutput = baseGateway.findById(changeAgencyInput);

            var accountDto = AccountBuilder.buildFullAccountDto().createFrom(accountOutput.get());
            var account = factoryAggregate.create(accountDto);

            var newAccountDto = new AccountDTO(
                    accountDto.number(),
                    changeAgencyInput.newAgencyNumber(),
                    accountDto.balance(),
                    accountDto.password(),
                    accountDto.active(),
                    accountDto.cpf(),
                    accountDto.transactions(),
                    accountDto.creditCard()
            );

            account.deactivate();
            baseGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));

            var newAccount = factoryAggregate.create(newAccountDto);
            baseGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(newAccount.build()));

            return EmptyOutput.INSTANCE;
        } catch (Exception e) {
            throw new GenericException("Generic error", e);
        }
    }
}
