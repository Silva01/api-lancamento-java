package silva.daniel.project.app.domain.account.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account", schema = "account")
public class Account {

    @EmbeddedId
    private AccountKey keys;
    private BigDecimal balance;
    private String password;
    private boolean active;
    private String cpf;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_card_number", referencedColumnName = "number")
    private CreditCard creditCard;

    @OneToMany(mappedBy = "originAccountNumber", cascade = CascadeType.ALL)
    private List<Transaction> transactions;
}
