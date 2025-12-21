package br.net.silva.business.exception;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

public class TransactionDuplicateExceptionDomain extends DomainGenericException {

    public TransactionDuplicateExceptionDomain(String message) {
        super(message);
    }
}
