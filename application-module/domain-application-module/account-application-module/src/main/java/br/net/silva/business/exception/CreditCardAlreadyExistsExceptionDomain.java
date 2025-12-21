package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class CreditCardAlreadyExistsExceptionDomain extends DomainGenericException {

    public CreditCardAlreadyExistsExceptionDomain(String message) {
        super(message);
    }
}
