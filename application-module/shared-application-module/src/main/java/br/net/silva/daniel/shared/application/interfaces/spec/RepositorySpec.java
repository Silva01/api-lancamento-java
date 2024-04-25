package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;

public interface RepositorySpec {
    MapperSpec withBaseRepository(ApplicationBaseGateway<?> baseRepository);
}
