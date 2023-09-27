package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.domain.Transaction;
import br.net.silva.daniel.enuns.TransactionType;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionComponent {
    BigDecimal sumPricePeerType(TransactionType type);
    Transaction get();
}
