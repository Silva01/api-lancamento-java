package br.net.silva.daniel.shared.application.repository;

import java.util.List;
import java.util.Optional;

public interface FindApplicationBaseRepository<R> {
    Optional<R> findById(ParamRepository param);
    List<R> findAll();
}
