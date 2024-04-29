package br.net.silva.business.decorator;

import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.decorator.ValidationDecorator;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public final class AccountAlreadyExistsForNewAgencyDecorator implements ValidationDecorator<AccountOutput> {

    private final Optional<AccountOutput> accountWithNewAgency;

    public AccountAlreadyExistsForNewAgencyDecorator(Optional<AccountOutput> accountWithNewAgency) {
        this.accountWithNewAgency = accountWithNewAgency;
    }

    @Override
    public void decorator(AccountOutput accountOutput) throws GenericException {
        if (accountWithNewAgency.isPresent())
            throw new AccountAlreadyExistsForNewAgencyException("Account With new agency already exists");
    }
}
