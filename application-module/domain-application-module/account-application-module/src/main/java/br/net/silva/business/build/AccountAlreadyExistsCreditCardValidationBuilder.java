package br.net.silva.business.build;

import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.business.validations.AccountAlreadyExistsCreditCardValidation;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.gateway.Repository;

import java.util.Optional;

public class AccountAlreadyExistsCreditCardValidationBuilder implements ObjectBuilder<IValidations> {

    private Repository<Optional<AccountOutput>> findAccountRepository;

    public AccountAlreadyExistsCreditCardValidationBuilder withRepository(Repository<Optional<AccountOutput>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
        return this;
    }

    @Override
    public IValidations build() {
        return new AccountAlreadyExistsCreditCardValidation(findAccountRepository);
    }
}
