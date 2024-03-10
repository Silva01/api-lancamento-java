package br.net.silva.business.exception;

import br.net.silva.daniel.shared.application.exception.GenericException;

public class AccountNotExistsException extends GenericException {
    public AccountNotExistsException(String message) {
        super(message);
    }
}
