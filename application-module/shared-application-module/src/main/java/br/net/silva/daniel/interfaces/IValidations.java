package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;

public interface IValidations<T> {
    void validate(T param) throws GenericException;
}
