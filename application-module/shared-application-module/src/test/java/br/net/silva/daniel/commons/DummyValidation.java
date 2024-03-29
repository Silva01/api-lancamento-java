package br.net.silva.daniel.commons;

import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.value_object.Source;

public class DummyValidation implements IValidations {
    @Override
    public void validate(Source input) throws GenericException {
        System.out.println("Validating input");
    }
}
