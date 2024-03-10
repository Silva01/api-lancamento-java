package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.utils.ObjectCreatorUtils;

public abstract class ValidationBuilder {

    private ValidationBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static <T extends ObjectBuilder<IValidations>> T create(Class<T> type) throws Exception {
        return ObjectCreatorUtils.createObjectWithEmptyConstructor(type);
    }
}
