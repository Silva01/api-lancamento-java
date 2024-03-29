package br.net.silva.daniel.commons.build;

import br.net.silva.daniel.commons.DummyValidation;
import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.interfaces.IValidations;

public class DammyValidationBuilder implements ObjectBuilder<IValidations> {
    @Override
    public IValidations build() throws Exception {
        return new DummyValidation();
    }
}
