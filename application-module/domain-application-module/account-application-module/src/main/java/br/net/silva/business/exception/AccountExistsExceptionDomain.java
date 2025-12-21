package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class AccountExistsExceptionDomain extends DomainGenericException {
    public AccountExistsExceptionDomain(String message) {
        super(message);
    }
}
