package br.net.silva.daniel.shared.application.interfaces.spec;

import br.net.silva.daniel.shared.application.repository.ApplicationBaseRepository;

public interface RepositorySpec<T> {
    MapperSpec withBaseRepository(ApplicationBaseRepository<T> baseRepository);
}
