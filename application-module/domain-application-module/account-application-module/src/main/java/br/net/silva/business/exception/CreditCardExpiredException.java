package br.net.silva.business.exception;

import br.net.silva.daniel.shared.application.exception.GenericException;

public class CreditCardExpiredException extends GenericException {
    public CreditCardExpiredException(String message) {
        super(message);
    }
}
