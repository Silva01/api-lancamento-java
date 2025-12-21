package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class CreditCardExpiredExceptionDomain extends DomainGenericException {
    public CreditCardExpiredExceptionDomain(String message) {
        super(message);
    }
}
