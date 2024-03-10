package br.net.silva.business.exception;

import br.net.silva.daniel.shared.application.exception.GenericException;

public class TransactionNotExistsException extends GenericException {

    public TransactionNotExistsException(String message) {
        super(message);
    }
    
}
