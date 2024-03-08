package br.net.silva.daniel.build;

import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.utils.ObjectCreatorUtils;

public abstract class ValidationBuilder {

    private ValidationBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static <T extends ObjectBuilder<IValidations>> T create(Class<T> type) throws Exception {
        return ObjectCreatorUtils.createObjectWithEmptyConstructor(type);
    }
}
