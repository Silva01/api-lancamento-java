package br.net.silva.daniel.repository;

import java.util.List;
import java.util.Optional;

public interface FindApplicationBaseRepository<R> {
    Optional<R> findById();
    List<R> findAll();
}
