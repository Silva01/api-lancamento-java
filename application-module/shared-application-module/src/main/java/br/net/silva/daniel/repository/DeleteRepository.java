package br.net.silva.daniel.repository;

public interface DeleteRepository<T> {
    void delete(T entity);
    void deleteById(Long id);
}
