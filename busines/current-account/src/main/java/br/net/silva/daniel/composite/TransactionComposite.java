package br.net.silva.daniel.composite;

import br.net.silva.daniel.domain.Transaction;
import br.net.silva.daniel.enuns.TransactionType;
import br.net.silva.daniel.interfaces.TransactionComponent;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionComposite implements TransactionComponent {

    private final List<TransactionComponent> transactionComponents;

    public TransactionComposite(TransactionComponent... transactionComponents) {
        this.transactionComponents = List.of(transactionComponents);
    }

    public TransactionComposite (List<TransactionComponent> transactionComponents) {
        this.transactionComponents = transactionComponents;
    }

    @Override
    public BigDecimal sumPricePeerType(TransactionType type) {
        return transactionComponents.stream().map(transactionComponent -> transactionComponent.sumPricePeerType(type)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public Transaction get() {
        throw new UnsupportedOperationException();
    }


    public List<TransactionComponent> transactionsPeerType(TransactionType type) {
        return transactionComponents.stream().filter(t -> t.get().getType().equals(type)).collect(Collectors.toList());
    }
}
