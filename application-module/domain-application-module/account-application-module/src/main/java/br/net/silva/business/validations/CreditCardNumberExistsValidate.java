package br.net.silva.business.validations;

import br.net.silva.business.exception.CreditCardDeactivatedException;
import br.net.silva.business.exception.CreditCardNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.util.Optional;

public class CreditCardNumberExistsValidate implements Validation<AccountOutput> {

    @Override
    public void validate(Optional<AccountOutput> opt) throws GenericException {
        final var account = opt.orElseThrow(GenericErrorUtils::executeExceptionNotPermissionOperation);
        final var creditCard = account.creditCard();

        if (creditCard == null) {
            throw new CreditCardNotExistsException("Credit card not exists in the account");
        }

        if (!creditCard.active()) {
            throw new CreditCardDeactivatedException("Credit card deactivated in the account");
        }

    }
}
