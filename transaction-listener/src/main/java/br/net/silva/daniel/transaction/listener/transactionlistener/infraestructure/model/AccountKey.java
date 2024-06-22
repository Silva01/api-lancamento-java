package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AccountKey implements Serializable {
    private Integer number;
    private Integer bankAgencyNumber;
}
