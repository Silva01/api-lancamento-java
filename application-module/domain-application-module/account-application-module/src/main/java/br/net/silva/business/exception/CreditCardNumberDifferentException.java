package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class CreditCardNumberDifferentException extends GenericException {

    public CreditCardNumberDifferentException(String message) {
        super(message);
    }
}
