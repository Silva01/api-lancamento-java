package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class AccountBalanceInsufficientException extends GenericException {
    public AccountBalanceInsufficientException(String message) {
        super(message);
    }
}
