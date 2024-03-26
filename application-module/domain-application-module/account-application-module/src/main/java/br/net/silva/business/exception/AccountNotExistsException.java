package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class AccountNotExistsException extends GenericException {
    public AccountNotExistsException(String message) {
        super(message);
    }
}
