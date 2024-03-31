package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;

public class DeactivateCreditCardUseCase implements UseCase<EmptyOutput> {
    private final ApplicationBaseGateway<AccountOutput> accountBaseGateway;

    private final IFactoryAggregate<Account, AccountDTO> accountFactory;

    public DeactivateCreditCardUseCase(ApplicationBaseGateway<AccountOutput> accountBaseGateway) {
        this.accountBaseGateway = accountBaseGateway;
        this.accountFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        var input = (DeactivateCreditCardInput) param.input();
        var accountOutput = accountBaseGateway.findById(input).orElseThrow(() -> new AccountNotExistsException("Account not exists"));

        var account = accountFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));

        if (!account.build().creditCard().number().equals(input.creditCardNumber())) {
            throw new CreditCardNumberDifferentException("Credit card number is different the credit card informed for the user");
        }

        account.deactivateCreditCard();
        accountBaseGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));

        return EmptyOutput.INSTANCE;
    }
}
