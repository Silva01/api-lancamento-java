package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.build.Builder;
import br.net.silva.daniel.shared.application.interfaces.UseCase;

public interface UseCaseBuilder<T> {
    UseCaseBuilder<T> andWithBuilderUseCase(Builder<UseCase<?>> useCaseBuilder);
    T build();
}
