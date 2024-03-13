package br.net.silva.daniel.shared.application.interfaces.spec;

public interface PrepareUseCaseSpec<T> {
    RepositorySpec prepareUseCaseFrom(Class<T> clazz);
}
