package br.net.silva.daniel.exceptions;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class ClientDomainExceptionDomain extends DomainGenericException {
    public ClientDomainExceptionDomain(String message) {
        super(message);
    }
}
