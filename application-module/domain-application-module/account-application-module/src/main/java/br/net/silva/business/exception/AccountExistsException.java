package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class AccountExistsException extends GenericException {
    public AccountExistsException(String message) {
        super(message);
    }
}
