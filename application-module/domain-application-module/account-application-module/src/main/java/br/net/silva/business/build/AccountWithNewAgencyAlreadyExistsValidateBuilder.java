package br.net.silva.business.build;

import br.net.silva.business.validations.AccountWithNewAgencyAlreadyExistsValidate;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IValidations;

@Deprecated(forRemoval = true)
public final class AccountWithNewAgencyAlreadyExistsValidateBuilder implements ObjectBuilder<IValidations> {

    private FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    public AccountWithNewAgencyAlreadyExistsValidateBuilder withRepository(FindApplicationBaseGateway<AccountOutput> findAccountGateway) {
        this.findAccountGateway = findAccountGateway;
        return this;
    }

    @Override
    public IValidations build() throws Exception {
        return new AccountWithNewAgencyAlreadyExistsValidate(findAccountGateway);
    }
}
