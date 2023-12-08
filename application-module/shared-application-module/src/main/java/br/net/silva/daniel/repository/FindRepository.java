package br.net.silva.daniel.repository;

import java.util.List;

public interface FindRepository<T> {
    T findById(Long id);
    List<T> findAll();
}
