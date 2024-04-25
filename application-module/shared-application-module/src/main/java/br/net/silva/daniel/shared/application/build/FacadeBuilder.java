package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.interfaces.spec.UseCaseBuilderSpec;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public interface FacadeBuilder {

    static UseCaseBuilderSpec<br.net.silva.daniel.shared.application.build.Builder<GenericFacadeDelegate>> make() {
        return new Builder<>();
    }

    class Builder<T> implements UseCaseBuilderSpec<br.net.silva.daniel.shared.application.build.Builder<T>> {

        private final Queue<UseCase<?>> useCaseQueue;

        public Builder() {
            useCaseQueue = new LinkedList<>();
        }

        @Override
        public br.net.silva.daniel.shared.application.build.Builder<T> withBuilderUseCases(br.net.silva.daniel.shared.application.build.Builder<UseCase>... useCaseBuilders) {
            Stream.of(useCaseBuilders).forEach(useCase -> {
                try {
                    useCaseQueue.add(useCase.build());
                } catch (Exception e) {
                    throw GenericErrorUtils.executeErrorAtExecuteBuilder(e);
                }
            });
            return () -> (T) new GenericFacadeDelegate(useCaseQueue);
        }
    }
}
