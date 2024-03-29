package br.net.silva.daniel.shared.application.gateway;

import java.util.List;

public interface SaveApplicationBaseGateway<R> {
    R save(R entity);
    void saveAll(List<R> paramList);
}
