package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class CreditCardNotExistsException extends GenericException {
    public CreditCardNotExistsException(String message) {
        super(message);
    }
}
