package br.net.silva.business.exception;

import br.net.silva.daniel.exception.GenericException;

public class AccountExistsForCPFInformatedException extends GenericException {
    public AccountExistsForCPFInformatedException(String message) {
        super(message);
    }
}
