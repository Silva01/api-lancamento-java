package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.interfaces.spec.UseCaseBuilderSpec;
import br.net.silva.daniel.shared.application.interfaces.spec.ValidationSpec;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

public interface FacadeBuilder {

    static UseCaseBuilderSpec<ValidationSpec<br.net.silva.daniel.shared.application.build.Builder<GenericFacadeDelegate>>> make() {
        return new Builder<>();
    }

    class Builder<T> implements UseCaseBuilderSpec<ValidationSpec<br.net.silva.daniel.shared.application.build.Builder<T>>>, ValidationSpec<br.net.silva.daniel.shared.application.build.Builder<T>> {

        private final Queue<UseCase> useCaseQueue;
        private final List<IValidations> validations;

        public Builder() {
            useCaseQueue = new LinkedList<>();
            validations = new ArrayList<>();
        }

        @Override
        public ValidationSpec withBuilderUseCases(br.net.silva.daniel.shared.application.build.Builder<UseCase>... useCaseBuilders) {
            Stream.of(useCaseBuilders).forEach(useCase -> {
                try {
                    useCaseQueue.add(useCase.build());
                } catch (Exception e) {
                    throw GenericErrorUtils.executeErrorAtExecuteBuilder(e);
                }
            });
            return this;
        }

        @Override
        public br.net.silva.daniel.shared.application.build.Builder<T> withBuilderValidations(ObjectBuilder<IValidations>... validationBuilders) {
            Stream.of(validationBuilders).forEach(validation -> {
                try {
                    validations.add(validation.build());
                } catch (Exception e) {
                    throw GenericErrorUtils.executeErrorAtExecuteBuilder(e);
                }
            });
            return () -> (T) new GenericFacadeDelegate<>(useCaseQueue, validations);
        }
    }
}
