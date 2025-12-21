package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class TransactionNotExistsExceptionDomain extends DomainGenericException {

    public TransactionNotExistsExceptionDomain(String message) {
        super(message);
    }
    
}
