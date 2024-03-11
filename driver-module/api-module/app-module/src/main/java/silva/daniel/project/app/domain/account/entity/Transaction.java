package silva.daniel.project.app.domain.account.entity;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "transaction", schema = "account")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private TransactionTypeEnum type;
    private Integer originAccountNumber;
    private Integer destinationAccountNumber;
    private Long idempotencyId;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_card_number", referencedColumnName = "number")
    private CreditCard creditCardNumber;
}
