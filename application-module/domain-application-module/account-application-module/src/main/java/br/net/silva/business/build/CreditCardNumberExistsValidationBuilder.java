package br.net.silva.business.build;

import br.net.silva.business.validations.CreditCardNumberExistsValidate;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IValidations;

@Deprecated(forRemoval = true)
public class CreditCardNumberExistsValidationBuilder implements ObjectBuilder<IValidations> {

    private FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    public CreditCardNumberExistsValidationBuilder withRepository(FindApplicationBaseGateway<AccountOutput> findAccountGateway) {
        this.findAccountGateway = findAccountGateway;
        return this;
    }

    @Override
    public IValidations build() throws Exception {
        return new CreditCardNumberExistsValidate(findAccountGateway);
    }
}
