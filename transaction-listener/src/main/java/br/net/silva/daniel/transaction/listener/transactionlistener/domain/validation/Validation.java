package br.net.silva.daniel.transaction.listener.transactionlistener.domain.validation;

import br.net.silva.daniel.shared.business.exception.GenericException;

public interface Validation <T> {
    void validate(T t) throws GenericException;

    default void validate(T t, String message) throws GenericException {
        try {
            validate(t);
        } catch (GenericException e) {
            throw new GenericException(String.format("%s %s", message, e.getMessage()));
        }
    }
}
