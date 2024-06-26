package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.validations.AccountExistsAndActiveValidate;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

@ValidateStrategyOn(validations = {AccountExistsAndActiveValidate.class})
public class DeactivateAccountUseCase implements UseCase<AccountOutput> {

    private final ApplicationBaseGateway<AccountOutput> baseAccountGateway;

    public DeactivateAccountUseCase(ApplicationBaseGateway<AccountOutput> baseAccountGateway) {
        this.baseAccountGateway = baseAccountGateway;
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var optAccount = baseAccountGateway.findById((ParamGateway) param.input());

        var accountOutput = execValidate(optAccount).extract();
        var account = AccountBuilder.buildAggregate().createFrom(accountOutput);
        account.deactivate();
        return baseAccountGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));
    }
}
