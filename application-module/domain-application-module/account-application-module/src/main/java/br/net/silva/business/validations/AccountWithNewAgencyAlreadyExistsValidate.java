package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public class AccountWithNewAgencyAlreadyExistsValidate implements Validation<AccountOutput> {

    @Override
    public void validate(Optional<AccountOutput> opt) throws GenericException {
        if (opt.isPresent())
            throw new AccountAlreadyExistsForNewAgencyException("Account With new agency already exists");

    }
}
