package br.net.silva.business.validations;

import br.net.silva.business.exception.CreditCardDeactivatedExceptionDomain;
import br.net.silva.business.exception.CreditCardNotExistsExceptionDomain;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.util.Optional;

public class CreditCardNumberExistsValidate implements Validation<AccountOutput> {

    @Override
    public void validate(Optional<AccountOutput> opt) throws DomainGenericException {
        final var account = opt.orElseThrow(GenericErrorUtils::executeExceptionNotPermissionOperation);
        final var creditCard = account.creditCard();

        if (creditCard == null) {
            throw new CreditCardNotExistsExceptionDomain("Credit card not exists in the account");
        }

        if (!creditCard.active()) {
            throw new CreditCardDeactivatedExceptionDomain("Credit card deactivated in the account");
        }

    }
}
