package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class AccountExistsForCPFInformatedExceptionDomain extends DomainGenericException {
    public AccountExistsForCPFInformatedExceptionDomain(String message) {
        super(message);
    }
}
