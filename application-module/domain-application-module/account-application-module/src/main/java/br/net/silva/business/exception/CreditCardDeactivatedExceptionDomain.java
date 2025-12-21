package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class CreditCardDeactivatedExceptionDomain extends DomainGenericException {
    public CreditCardDeactivatedExceptionDomain(String message) {
        super(message);
    }
}
