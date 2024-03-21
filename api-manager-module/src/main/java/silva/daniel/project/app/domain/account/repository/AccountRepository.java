package silva.daniel.project.app.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silva.daniel.project.app.domain.account.entity.Account;
import silva.daniel.project.app.domain.account.entity.AccountKey;

public interface AccountRepository extends JpaRepository<Account, AccountKey> {
}
