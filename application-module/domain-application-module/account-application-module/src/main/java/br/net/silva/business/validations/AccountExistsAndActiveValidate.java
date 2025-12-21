package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountDeactivatedExceptionDomain;
import br.net.silva.business.exception.AccountNotExistsExceptionDomain;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;

import java.util.Optional;

public class AccountExistsAndActiveValidate implements Validation<AccountOutput> {

    @Override
    public void validate(Optional<AccountOutput> optAccount) throws DomainGenericException {
        if (optAccount.isEmpty()) {
            throw new AccountNotExistsExceptionDomain("Account not found");
        }

        if (!optAccount.get().active()) {
            throw new AccountDeactivatedExceptionDomain("Account is Deactivated");
        }
    }
}
