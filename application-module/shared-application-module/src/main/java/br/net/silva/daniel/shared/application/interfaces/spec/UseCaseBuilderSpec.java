package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.build.Builder;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.business.exception.GenericException;

public interface UseCaseBuilderSpec<T> {
    T withBuilderUseCases(Builder<UseCase<?>>... useCaseBuilders);

    default T andWithBuilderUseCase(Builder<UseCase<?>> useCaseBuilder) {
        throw new RuntimeException("Not Permmited call");
    }

    default UseCaseBuilderSpec<T> withBuilderUseCase(Builder<UseCase<?>> useCaseBuilder) {
        throw new RuntimeException("Not Permmited call");
    }
}
