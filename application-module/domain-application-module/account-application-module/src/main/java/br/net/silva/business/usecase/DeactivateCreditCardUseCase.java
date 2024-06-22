package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.decorator.ValidateCreditCardNumberDecorator;
import br.net.silva.business.validations.AccountExistsValidate;
import br.net.silva.business.validations.CreditCardNumberExistsValidate;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;

@ValidateStrategyOn(validations = {AccountExistsValidate.class, CreditCardNumberExistsValidate.class})
public class DeactivateCreditCardUseCase implements UseCase<AccountOutput> {
    private final ApplicationBaseGateway<AccountOutput> accountBaseGateway;

    private final IFactoryAggregate<Account, AccountDTO> accountFactory;

    public DeactivateCreditCardUseCase(ApplicationBaseGateway<AccountOutput> accountBaseGateway) {
        this.accountBaseGateway = accountBaseGateway;
        this.accountFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var input = (DeactivateCreditCardInput) param.input();
        var accountOpt = accountBaseGateway.findById(input);

        final var accountOutput = execValidate(
                accountOpt,
                new ValidateCreditCardNumberDecorator(input.creditCardNumber())).extract();

        var account = accountFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));
        account.deactivateCreditCard();
        return accountBaseGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));
    }
}
