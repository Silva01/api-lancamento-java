package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.GenericException;

public class AccountAlreadyExistsForNewAgencyException extends GenericException {
    public AccountAlreadyExistsForNewAgencyException(String message) {
        super(message);
    }
}
