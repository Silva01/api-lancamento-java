package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class CreditCardNotExistsExceptionDomain extends DomainGenericException {
    public CreditCardNotExistsExceptionDomain(String message) {
        super(message);
    }
}
