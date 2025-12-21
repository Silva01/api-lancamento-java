package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyExceptionDomain;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;

import java.util.Optional;

public class AccountWithNewAgencyAlreadyExistsValidate implements Validation<AccountOutput> {

    @Override
    public void validate(Optional<AccountOutput> opt) throws DomainGenericException {
        if (opt.isPresent())
            throw new AccountAlreadyExistsForNewAgencyExceptionDomain("Account With new agency already exists");

    }
}
