package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class AccountDeactivatedException extends GenericException {
    public AccountDeactivatedException(String message) {
        super(message);
    }
}
