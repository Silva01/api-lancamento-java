package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.value_object.Source;

public interface IValidations {
    void validate(Source input) throws GenericException;
}
