package br.net.silva.daniel.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class ClientNotExistsExceptionDomain extends DomainGenericException {
    public ClientNotExistsExceptionDomain(String message) {
        super(message);
    }
}
