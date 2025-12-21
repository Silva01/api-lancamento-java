package br.net.silva.business.decorator;

import br.net.silva.business.exception.AccountAlreadyExistsForNewAgencyExceptionDomain;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.decorator.ValidationDecorator;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;

import java.util.Optional;

public final class AccountAlreadyExistsForNewAgencyDecorator implements ValidationDecorator<AccountOutput> {

    private final Optional<AccountOutput> accountWithNewAgency;

    public AccountAlreadyExistsForNewAgencyDecorator(Optional<AccountOutput> accountWithNewAgency) {
        this.accountWithNewAgency = accountWithNewAgency;
    }

    @Override
    public void decorator(AccountOutput accountOutput) throws DomainGenericException {
        if (accountWithNewAgency.isPresent())
            throw new AccountAlreadyExistsForNewAgencyExceptionDomain("Account With new agency already exists");
    }
}
