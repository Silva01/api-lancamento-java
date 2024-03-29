package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.interfaces.IValidations;

public interface ValidationSpec<T> {
    T withBuilderValidations(ObjectBuilder<IValidations>... validationBuilders);
}
