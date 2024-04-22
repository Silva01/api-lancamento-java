package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.value_object.Source;

@Deprecated(forRemoval = true)
public interface IValidations {
    @Deprecated(forRemoval = true)
    void validate(Source input) throws GenericException;
}
