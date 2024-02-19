package br.net.silva.business.exception;

import br.net.silva.daniel.exception.GenericException;

public class TransactionNotExistsException extends GenericException {

    public TransactionNotExistsException(String message) {
        super(message);
    }
    
}
