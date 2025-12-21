package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class AccountDeactivatedExceptionDomain extends DomainGenericException {
    public AccountDeactivatedExceptionDomain(String message) {
        super(message);
    }
}
