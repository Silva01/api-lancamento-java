package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.interfaces.spec.MapperSpec;
import br.net.silva.daniel.shared.application.interfaces.spec.PrepareUseCaseSpec;
import br.net.silva.daniel.shared.application.interfaces.spec.RepositorySpec;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseRepository;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.lang.reflect.Constructor;

public interface UseCaseBuilder {

    static PrepareUseCaseSpec make() {
        return new Builder();
    }

    @SuppressWarnings("unchecked")
    static <T> br.net.silva.daniel.shared.application.build.Builder<UseCase> makeTo(ApplicationBaseRepository<?> baseRepository, GenericResponseMapper mapper, Class<T> clazz) {
        return new Builder(baseRepository, clazz).withGenericMapper(mapper);
    }

    class Builder<T extends UseCase> implements PrepareUseCaseSpec<T>, RepositorySpec<T>, MapperSpec<T> {

        private Class<T> clazz;
        private ApplicationBaseRepository<?> baseRepository;
        private GenericResponseMapper mapper;

        public Builder() {
        }

        public Builder(ApplicationBaseRepository<?> baseRepository, Class<T> clazz) {
            this.baseRepository = baseRepository;
            this.clazz = clazz;
        }

        @Override
        public br.net.silva.daniel.shared.application.build.Builder<T> withGenericMapper(GenericResponseMapper mapper) {
            this.mapper = mapper;
            return () -> {
                try {
                    Constructor[] constructors = clazz.getConstructors();
                    for (Constructor constructor : constructors) {
                        if (constructor.getParameterCount() == 1) {
                            return clazz.cast(constructor.newInstance(baseRepository));
                        } else if (constructor.getParameterCount() == 2) {
                            return clazz.cast(constructor.newInstance(baseRepository, this.mapper));
                        }
                    }
                } catch (Exception e) {
                    throw GenericErrorUtils.executeException("Invalid constructor for use case");
                }

                throw GenericErrorUtils.executeException("Invalid constructor for use case");
            };
        }

        @Override
        public RepositorySpec prepareUseCaseFrom(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        @Override
        public MapperSpec withBaseRepository(ApplicationBaseRepository<T> baseRepository) {
            this.baseRepository = baseRepository;
            return this;
        }
    }
}
