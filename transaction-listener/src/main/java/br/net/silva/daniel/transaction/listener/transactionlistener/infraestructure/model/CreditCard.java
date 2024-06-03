package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model;

import br.net.silva.daniel.enuns.FlagEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_card", schema = "account")
public class CreditCard {

    @Id
    private String number;
    private Integer cvv;

    @Enumerated(EnumType.STRING)
    private FlagEnum flag;
    private BigDecimal balance;
    private LocalDate expirationDate;
    private boolean active;
}
