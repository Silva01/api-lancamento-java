package silva.daniel.project.app.domain.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silva.daniel.project.app.domain.account.entity.CreditCard;

public interface CreditCardRepository extends JpaRepository<CreditCard, String> {
}
