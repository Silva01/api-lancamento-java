package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class CreditCardDeactivatedException extends GenericException {
    public CreditCardDeactivatedException(String message) {
        super(message);
    }
}
