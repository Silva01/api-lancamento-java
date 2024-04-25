package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;

public interface RepositorySpec<T> {
    MapperSpec withBaseRepository(ApplicationBaseGateway<?> baseRepository);
}
