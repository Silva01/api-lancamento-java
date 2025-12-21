package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class AccountAlreadyExistsForNewAgencyExceptionDomain extends DomainGenericException {
    public AccountAlreadyExistsForNewAgencyExceptionDomain(String message) {
        super(message);
    }
}
