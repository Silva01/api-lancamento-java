package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class CreditCardNumberDifferentExceptionDomain extends DomainGenericException {

    public CreditCardNumberDifferentExceptionDomain(String message) {
        super(message);
    }
}
