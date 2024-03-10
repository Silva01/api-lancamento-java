package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.interfaces.spec.MapperSpec;
import br.net.silva.daniel.shared.application.interfaces.spec.PrepareUseCaseSpec;
import br.net.silva.daniel.shared.application.interfaces.spec.RepositorySpec;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.ApplicationBaseRepository;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class UseCaseBuilder {

    private UseCaseBuilder() {
    }

    public static PrepareUseCaseSpec make() {
        return new Builder();
    }

    public static class Builder implements PrepareUseCaseSpec, RepositorySpec, MapperSpec {

        private List<Class<?>> clazzList;
        private ApplicationBaseRepository<?> baseRepository;
        private GenericResponseMapper mapper;

        @Override
        public br.net.silva.daniel.shared.application.build.Builder<Queue<UseCase>> withGenericMapper(GenericResponseMapper mapper) {
            this.mapper = mapper;
            return () -> {
                Queue<UseCase> useCaseQueue = new LinkedList<>();
                clazzList.forEach(clazz -> {
                    try {
                        Constructor[] constructors = clazz.getConstructors();
                        for (Constructor constructor : constructors) {
                            if (constructor.getParameterCount() == 1) {
                                useCaseQueue.add((UseCase) constructor.newInstance(baseRepository));
                            } else if (constructor.getParameterCount() == 2) {
                                useCaseQueue.add((UseCase) constructor.newInstance(baseRepository, this.mapper));
                            }
                        }
                    } catch (Exception e) {
                        throw GenericErrorUtils.executeException("Invalid constructor for use case");
                    }
                });

                return useCaseQueue;
            };
        }

        @Override
        public RepositorySpec prepareUseCasesFrom(Class... clazz) {
            this.clazzList = Arrays.asList(clazz);
            return this;
        }

        @Override
        public <T> MapperSpec withBaseRepository(ApplicationBaseRepository<T> baseRepository) {
            this.baseRepository = baseRepository;
            return this;
        }
    }
}
