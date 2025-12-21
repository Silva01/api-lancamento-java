package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class AccountNotExistsExceptionDomain extends DomainGenericException {
    public AccountNotExistsExceptionDomain(String message) {
        super(message);
    }
}
