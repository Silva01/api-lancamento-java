package br.net.silva.business.exception;

import br.net.silva.daniel.exception.GenericException;

public class AccountAlreadyActiveException extends GenericException {
    public AccountAlreadyActiveException(String message) {
        super(message);
    }
}
