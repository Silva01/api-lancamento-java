package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.validations.AccountExistsAndDeactivatedValidate;
import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

@ValidateStrategyOn(validations = {AccountExistsAndDeactivatedValidate.class})
public final class ActivateAccountUseCase implements UseCase<AccountOutput> {

    private final ApplicationBaseGateway<AccountOutput> baseGateway;

    private final CreateAccountByAccountDTOFactory accountFactory;

    public ActivateAccountUseCase(ApplicationBaseGateway<AccountOutput> baseGateway) {
        this.baseGateway = baseGateway;
        this.accountFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        try {
            var dto = (ActivateAccount) param.input();
            var accountOutput = execValidate(baseGateway.findById(dto)).extract();

            var account = accountFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));
            account.activate();

            return baseGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
