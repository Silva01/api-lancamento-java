package silva.daniel.project.app.domain.client.entity.repository;

import org.springframework.data.repository.CrudRepository;
import silva.daniel.project.app.domain.client.entity.Client;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Optional<Client> findByCpf(String cpf);
}
