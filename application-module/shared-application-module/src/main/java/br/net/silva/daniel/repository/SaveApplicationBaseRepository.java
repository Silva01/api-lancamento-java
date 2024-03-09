package br.net.silva.daniel.repository;

import java.util.List;

public interface SaveApplicationBaseRepository<R> {
    R save(ParamRepository param);
    void saveAll(List<ParamRepository> paramList);
}
