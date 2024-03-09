package br.net.silva.daniel.repository;

import java.util.List;

public interface SaveApplicationBaseRepository<R> {
    R save(R entity);
    void saveAll(List<R> paramList);
}
