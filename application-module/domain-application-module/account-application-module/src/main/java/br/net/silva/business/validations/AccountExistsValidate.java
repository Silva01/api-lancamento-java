package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountNotExistsExceptionDomain;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;

import java.util.Optional;

public class AccountExistsValidate implements Validation<AccountOutput> {

    @Override
    public void validate(Optional<AccountOutput> opt) throws DomainGenericException {
        if (opt.isEmpty()) {
            throw new AccountNotExistsExceptionDomain("Account not exists");
        }
    }
}
