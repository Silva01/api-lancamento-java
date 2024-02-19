package br.net.silva.business.exception;

import br.net.silva.daniel.exception.GenericException;

public class CreditCardDeactivatedException extends GenericException {
    public CreditCardDeactivatedException(String message) {
        super(message);
    }
}
