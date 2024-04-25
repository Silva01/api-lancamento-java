package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.interfaces.spec.UseCaseBuilderSpec;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Stream;

public interface FacadeBuilder {

    static UseCaseBuilderSpec<Builder<GenericFacadeDelegate>> make() {
        return new FacadeProcessor();
    }

    class FacadeProcessor implements UseCaseBuilderSpec<Builder<GenericFacadeDelegate>> {

        private final Queue<UseCase<?>> useCaseQueue;

        public FacadeProcessor() {
            useCaseQueue = new LinkedList<>();
        }

        @Override
        public Builder<GenericFacadeDelegate> withBuilderUseCases(Builder<UseCase<?>>... useCaseBuilders) {
            Stream.of(useCaseBuilders).forEach(this::addUseCase);
            return () -> new GenericFacadeDelegate(useCaseQueue);
        }

        @Override
        public UseCaseBuilderSpec<Builder<GenericFacadeDelegate>> withBuilderUseCase(Builder<UseCase<?>> useCaseBuilder) {
            addUseCase(useCaseBuilder);
            return this;
        }

        @Override
        public Builder<GenericFacadeDelegate> andWithBuilderUseCase(Builder<UseCase<?>> useCaseBuilder) {
            addUseCase(useCaseBuilder);
            return () -> new GenericFacadeDelegate(useCaseQueue);
        }

        private void addUseCase(Builder<UseCase<?>> useCaseBuilder) {
            try {
                addUseCase(useCaseBuilder);
            } catch (Exception e) {
                throw GenericErrorUtils.executeErrorAtExecuteBuilder(e);
            }
        }
    }
}
