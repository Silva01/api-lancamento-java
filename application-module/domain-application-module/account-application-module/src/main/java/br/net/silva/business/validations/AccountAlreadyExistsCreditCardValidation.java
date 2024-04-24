package br.net.silva.business.validations;

import br.net.silva.business.exception.CreditCardAlreadyExistsException;
import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.util.Optional;

public class AccountAlreadyExistsCreditCardValidation implements IValidations, Validation<AccountOutput> {

    private final FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    public AccountAlreadyExistsCreditCardValidation() {
        this.findAccountGateway = null;
    }

    @Deprecated(forRemoval = true)
    public AccountAlreadyExistsCreditCardValidation(FindApplicationBaseGateway<AccountOutput> findAccountGateway) {
        this.findAccountGateway = findAccountGateway;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var createCreditCardInput = (CreateCreditCardInput) input.input();
        var optionalAccount = findAccountGateway.findById(createCreditCardInput);

        var account = optionalAccount.get();

        if (account.creditCard() != null){
            throw new CreditCardAlreadyExistsException("Credit card already exists");
        }
    }

    @Override
    public void validate(Optional<AccountOutput> opt) throws GenericException {
        if (opt.isEmpty()) {
            throw GenericErrorUtils.executeExceptionNotPermissionOperation();
        }

        final var account = opt.get();

        if (account.creditCard() != null){
            throw new CreditCardAlreadyExistsException("Credit card already exists");
        }
    }
}
