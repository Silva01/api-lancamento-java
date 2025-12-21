package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class AccountAlreadyActiveExceptionDomain extends DomainGenericException {
    public AccountAlreadyActiveExceptionDomain(String message) {
        super(message);
    }
}
