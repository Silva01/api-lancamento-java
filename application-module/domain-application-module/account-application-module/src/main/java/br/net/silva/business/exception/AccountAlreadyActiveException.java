package br.net.silva.business.exception;

import br.net.silva.daniel.shared.application.exception.GenericException;

public class AccountAlreadyActiveException extends GenericException {
    public AccountAlreadyActiveException(String message) {
        super(message);
    }
}
