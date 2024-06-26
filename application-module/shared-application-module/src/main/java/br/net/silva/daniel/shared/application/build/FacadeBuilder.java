package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.interfaces.spec.UseCaseBuilder;
import br.net.silva.daniel.shared.application.interfaces.spec.UseCaseBuilderSpec;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.util.LinkedList;
import java.util.Queue;

public interface FacadeBuilder {

    static UseCaseBuilderSpec<GenericFacadeDelegate> make() {
        return new FacadeProcessor();
    }

    class FacadeProcessor implements UseCaseBuilderSpec<GenericFacadeDelegate> {

        private final Queue<UseCase<?>> useCaseQueue;

        public FacadeProcessor() {
            useCaseQueue = new LinkedList<>();
        }

        @Override
        public UseCaseBuilder<GenericFacadeDelegate> withBuilderUseCase(Builder<UseCase<?>> useCaseBuilder) {
            addUseCase(useCaseBuilder);
            return this;
        }

        @Override
        public UseCaseBuilder<GenericFacadeDelegate> andWithBuilderUseCase(Builder<UseCase<?>> useCaseBuilder) {
            return withBuilderUseCase(useCaseBuilder);
        }

        @Override
        public GenericFacadeDelegate build() {
            return new GenericFacadeDelegate(useCaseQueue);
        }

        private void addUseCase(Builder<UseCase<?>> useCaseBuilder) {
            try {
                this.useCaseQueue.add(useCaseBuilder.build());
            } catch (Exception e) {
                throw GenericErrorUtils.executeErrorAtExecuteBuilder(e);
            }
        }
    }
}
