package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.interfaces.spec.MapperSpec;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.lang.reflect.Constructor;

public interface UseCaseBuilder {

    @SuppressWarnings("unchecked")
    static <T, R> Builder<UseCase<?>> makeTo(ApplicationBaseGateway<?> baseRepository, GenericResponseMapper mapper, Class<T> clazz) {
        return new UseCaseProcessor(baseRepository, clazz).withGenericMapper(mapper);
    }

    class UseCaseProcessor implements MapperSpec {

        private final Class<?> clazz;
        private final ApplicationBaseGateway<?> baseRepository;

        public UseCaseProcessor(ApplicationBaseGateway<?> baseRepository, Class<?> clazz) {
            this.baseRepository = baseRepository;
            this.clazz = clazz;
        }

        @Override
        public Builder<UseCase<?>> withGenericMapper(GenericResponseMapper mapper) {
            return () -> {
                try {
                    Constructor<?>[] constructors = clazz.getConstructors();
                    for (Constructor<?> constructor : constructors) {
                        if (constructor.getParameterCount() == 1) {
                            return (UseCase<?>) clazz.cast(constructor.newInstance(baseRepository));
                        } else if (constructor.getParameterCount() == 2) {
                            return (UseCase<?>) clazz.cast(constructor.newInstance(baseRepository, mapper));
                        }
                    }
                } catch (Exception e) {
                    throw GenericErrorUtils.executeException("Invalid constructor for use case");
                }

                throw GenericErrorUtils.executeException("Invalid constructor for use case");
            };
        }
    }
}
