package silva.daniel.project.app.domain.account.entity;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transaction", schema = "account")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private TransactionTypeEnum type;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="origin_account_agency", referencedColumnName="bankAgencyNumber"),
            @JoinColumn(name="origin_account_number", referencedColumnName="number")
    })
    private Account originAccountNumber;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="destination_account_agency", referencedColumnName="bankAgencyNumber"),
            @JoinColumn(name="destination_account_number", referencedColumnName="number")
    })
    private Account destinationAccountNumber;

    private Long idempotencyId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_card_number", referencedColumnName = "number")
    private CreditCard creditCardNumber;
}
