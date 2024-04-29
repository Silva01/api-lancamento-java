package br.net.silva.business.validations;

import br.net.silva.business.exception.CreditCardAlreadyExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.util.Optional;

public class AccountAlreadyExistsCreditCardValidation implements Validation<AccountOutput> {

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
