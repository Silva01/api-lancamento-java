package br.net.silva.daniel.repository;

import java.util.List;

public interface FindApplicationBaseRepository<R> {
    R findById();
    List<R> findAll();
}
