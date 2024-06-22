package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class CreditCardAlreadyExistsException extends GenericException {

    public CreditCardAlreadyExistsException(String message) {
        super(message);
    }
}
