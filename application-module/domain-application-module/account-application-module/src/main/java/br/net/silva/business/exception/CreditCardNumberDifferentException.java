package br.net.silva.business.exception;

import br.net.silva.daniel.exception.GenericException;

public class CreditCardNumberDifferentException extends GenericException {

    public CreditCardNumberDifferentException(String message) {
        super(message);
    }
}
