package br.net.silva.daniel.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class ClientDeactivatedExceptionDomain extends DomainGenericException {
    public ClientDeactivatedExceptionDomain(String message) {
        super(message);
    }
}
