package br.net.silva.daniel.interfaces.spec;

public interface PrepareUseCaseSpec {
    <T> RepositorySpec prepareUseCaseFrom(Class<T> clazz);
}
