package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.value_object.Source;

public interface IValidations {
    void validate(Source input) throws GenericException;
}
