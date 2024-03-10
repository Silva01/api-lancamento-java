package br.net.silva.daniel.shared.application.repository;

import java.util.List;

public interface SaveApplicationBaseRepository<R> {
    R save(R entity);
    void saveAll(List<R> paramList);
}
