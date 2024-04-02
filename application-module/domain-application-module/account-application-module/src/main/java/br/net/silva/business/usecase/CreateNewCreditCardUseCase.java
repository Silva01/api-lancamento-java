package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;

public class CreateNewCreditCardUseCase implements UseCase<AccountOutput> {

    private final ApplicationBaseGateway<AccountOutput> baseGateway;

    private final IFactoryAggregate<Account, AccountDTO> aggregateFactory;

    public CreateNewCreditCardUseCase(ApplicationBaseGateway<AccountOutput> baseGateway) {
        this.baseGateway = baseGateway;
        this.aggregateFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        try {
            var newCreditCardInput = (CreateCreditCardInput) param.input();
            var creditCard = new CreditCard();

            var accountOutput = baseGateway.findById(newCreditCardInput);

            var account = aggregateFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput.get()));

            account.vinculateCreditCard(creditCard);
            return baseGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));
        } catch (Exception e) {
            throw new GenericException("Generic error", e);
        }
    }
}
