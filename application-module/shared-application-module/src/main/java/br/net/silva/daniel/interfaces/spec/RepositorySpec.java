package br.net.silva.daniel.interfaces.spec;

import br.net.silva.daniel.repository.ApplicationBaseRepository;

public interface RepositorySpec {
    <T> MapperSpec withBaseRepository(ApplicationBaseRepository<T> baseRepository);
}
