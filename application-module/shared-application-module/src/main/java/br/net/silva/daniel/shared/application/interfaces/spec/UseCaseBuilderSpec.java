package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.build.Builder;
import br.net.silva.daniel.shared.application.interfaces.UseCase;

public interface UseCaseBuilderSpec<T> extends UseCaseBuilder<T> {

    @Deprecated(forRemoval = true)
    Builder<T> withBuilderUseCases(Builder<UseCase<?>>... useCaseBuilders);

    default UseCaseBuilder<T> withBuilderUseCase(Builder<UseCase<?>> useCaseBuilder) {
        throw new RuntimeException("Not Permmited call");
    }
}
