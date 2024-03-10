package br.net.silva.business.exception;

import br.net.silva.daniel.shared.application.exception.GenericException;

public class CreditCardNotExistsException extends GenericException {
    public CreditCardNotExistsException(String message) {
        super(message);
    }
}
