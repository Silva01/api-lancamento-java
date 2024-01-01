package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;

public interface IValidations {
    void validate(IGenericPort param) throws GenericException;
}
