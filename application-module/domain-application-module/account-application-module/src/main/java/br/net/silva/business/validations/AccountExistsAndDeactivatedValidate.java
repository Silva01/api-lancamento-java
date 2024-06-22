package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public class AccountExistsAndDeactivatedValidate implements Validation<AccountOutput> {

    @Override
    public void validate(Optional<AccountOutput> opt) throws GenericException {
        if (opt.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        if (opt.get().active()) {
            throw new AccountAlreadyActiveException("Account already active");
        }
    }
}
