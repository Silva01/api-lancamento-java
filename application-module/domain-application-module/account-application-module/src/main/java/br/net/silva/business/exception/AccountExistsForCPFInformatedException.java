package br.net.silva.business.exception;

import br.net.silva.daniel.shared.application.exception.GenericException;

public class AccountExistsForCPFInformatedException extends GenericException {
    public AccountExistsForCPFInformatedException(String message) {
        super(message);
    }
}
