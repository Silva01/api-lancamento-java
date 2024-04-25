package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.build.Builder;
import br.net.silva.daniel.shared.application.interfaces.UseCase;

public interface UseCaseBuilderSpec<T> {
    <R> T withBuilderUseCases(Builder<UseCase<R>>... useCaseBuilders);
}
