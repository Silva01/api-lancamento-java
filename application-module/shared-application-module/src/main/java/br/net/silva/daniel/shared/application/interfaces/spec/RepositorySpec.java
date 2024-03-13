package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.gateway.ApplicationBaseRepository;

public interface RepositorySpec<T> {
    MapperSpec withBaseRepository(ApplicationBaseRepository<T> baseRepository);
}
