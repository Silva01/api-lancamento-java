package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @CreationTimestamp
    private LocalDateTime createdAt;
}
