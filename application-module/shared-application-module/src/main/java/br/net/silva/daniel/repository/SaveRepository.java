package br.net.silva.daniel.repository;

public interface SaveRepository<T> {
    T save(T entity);
}
