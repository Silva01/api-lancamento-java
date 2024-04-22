package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.interfaces.IValidations;

@Deprecated(forRemoval = true)
public interface ValidationSpec<T> {
    @Deprecated(forRemoval = true)
    T withBuilderValidations(ObjectBuilder<IValidations>... validationBuilders);
}
