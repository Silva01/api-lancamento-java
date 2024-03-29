package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class AccountAlreadyActiveException extends GenericException {
    public AccountAlreadyActiveException(String message) {
        super(message);
    }
}
