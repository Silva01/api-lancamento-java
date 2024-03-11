package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.interfaces.spec.MapperSpec;
import br.net.silva.daniel.shared.application.interfaces.spec.PrepareUseCaseSpec;
import br.net.silva.daniel.shared.application.interfaces.spec.RepositorySpec;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.ApplicationBaseRepository;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.lang.reflect.Constructor;

public interface UseCaseBuilder {

    static PrepareUseCaseSpec make() {
        return new Builder();
    }

    class Builder implements PrepareUseCaseSpec, RepositorySpec, MapperSpec<UseCase> {

        private Class clazz;
        private ApplicationBaseRepository<?> baseRepository;
        private GenericResponseMapper mapper;

        @Override
        public br.net.silva.daniel.shared.application.build.Builder<UseCase> withGenericMapper(GenericResponseMapper mapper) {
            this.mapper = mapper;
            return () -> {
                try {
                    Constructor[] constructors = clazz.getConstructors();
                    for (Constructor constructor : constructors) {
                        if (constructor.getParameterCount() == 1) {
                            return (UseCase) constructor.newInstance(baseRepository);
                        } else if (constructor.getParameterCount() == 2) {
                            return (UseCase) constructor.newInstance(baseRepository, this.mapper);
                        }
                    }
                } catch (Exception e) {
                    throw GenericErrorUtils.executeException("Invalid constructor for use case");
                }

                throw GenericErrorUtils.executeException("Invalid constructor for use case");
            };
        }

        @Override
        public RepositorySpec prepareUseCaseFrom(Class clazz) {
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
