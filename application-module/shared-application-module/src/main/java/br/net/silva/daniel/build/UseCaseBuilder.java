package br.net.silva.daniel.build;

import br.net.silva.daniel.interfaces.Output;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.utils.ObjectCreatorUtils;

public abstract class UseCaseBuilder {

    private UseCaseBuilder() {
        throw new IllegalStateException("Utility class");
    }

    public static <T extends ObjectBuilder<UseCase<Output>>> T buildUseCase(Class<T> type) throws Exception {
        return ObjectCreatorUtils.createObjectWithEmptyConstructor(type);
    }
}
