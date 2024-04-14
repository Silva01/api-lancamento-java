package silva.daniel.project.app.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silva.daniel.project.app.domain.account.entity.Account;
import silva.daniel.project.app.domain.account.entity.AccountKey;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, AccountKey> {
    Optional<Account> findByCpf(String cpf);
    List<Account> findAllByCpf(String cpf);
}
