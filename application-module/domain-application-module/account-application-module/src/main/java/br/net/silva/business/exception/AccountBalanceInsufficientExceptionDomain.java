package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class AccountBalanceInsufficientExceptionDomain extends DomainGenericException {
    public AccountBalanceInsufficientExceptionDomain(String message) {
        super(message);
    }
}
