package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.shared.application.interfaces.spec.MapperSpec;
import br.net.silva.daniel.shared.application.interfaces.spec.PrepareUseCaseSpec;
import br.net.silva.daniel.shared.application.interfaces.spec.RepositorySpec;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.ApplicationBaseRepository;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.lang.reflect.Constructor;

public abstract class UseCaseBuilder {

    private UseCaseBuilder() {
    }

    public static PrepareUseCaseSpec make() {
        return new Builder();
    }

    public static class Builder implements PrepareUseCaseSpec, RepositorySpec, MapperSpec {

        private Class<?> clazz;
        private ApplicationBaseRepository<?> baseRepository;
        private GenericResponseMapper mapper;

        @Override
        public <T> br.net.silva.daniel.shared.application.build.Builder<T> withGenericMapper(GenericResponseMapper mapper) {
            this.mapper = mapper;
            return () -> {
                Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
                for (Constructor<T> constructor : constructors) {
                    if (constructor.getParameterCount() == 1) {
                        return (T) constructor.newInstance(baseRepository);
                    } else if (constructor.getParameterCount() == 2) {
                        return (T) constructor.newInstance(baseRepository, mapper);
                    }

                    throw GenericErrorUtils.executeException("Invalid constructor for use case");
                }

                throw GenericErrorUtils.executeException("Class does not have a constructor with the required parameters");
            };
        }

        @Override
        public <T> RepositorySpec prepareUseCaseFrom(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        @Override
        public <T> MapperSpec withBaseRepository(ApplicationBaseRepository<T> baseRepository) {
            this.baseRepository = baseRepository;
            return this;
        }
    }
}
