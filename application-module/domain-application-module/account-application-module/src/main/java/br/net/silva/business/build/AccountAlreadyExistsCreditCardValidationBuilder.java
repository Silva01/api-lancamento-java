package br.net.silva.business.build;

import br.net.silva.business.validations.AccountAlreadyExistsCreditCardValidation;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IValidations;

public class AccountAlreadyExistsCreditCardValidationBuilder implements ObjectBuilder<IValidations> {

    private FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    public AccountAlreadyExistsCreditCardValidationBuilder withRepository(FindApplicationBaseGateway<AccountOutput> findAccountGateway) {
        this.findAccountGateway = findAccountGateway;
        return this;
    }

    @Override
    public IValidations build() throws Exception {
        return new AccountAlreadyExistsCreditCardValidation(findAccountGateway);
    }
}
