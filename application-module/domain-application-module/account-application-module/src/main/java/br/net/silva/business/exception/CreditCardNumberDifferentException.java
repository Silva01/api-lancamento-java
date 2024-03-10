package br.net.silva.business.exception;

import br.net.silva.daniel.shared.application.exception.GenericException;

public class CreditCardNumberDifferentException extends GenericException {

    public CreditCardNumberDifferentException(String message) {
        super(message);
    }
}
