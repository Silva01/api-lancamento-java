package br.net.silva.daniel.strategy;

import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.enuns.TransactionTypeEnum;

import java.math.BigDecimal;
import java.util.List;

public class CalculateStrategy {

    public static ICalculation calculationSale() {
        return (transactions, type) -> calculate(transactions, type).multiply(BigDecimal.valueOf(-1));
    }

    public static ICalculation calculationBuy() {
        return CalculateStrategy::calculate;
    }

    private static BigDecimal calculate(List<TransactionDTO> transactions, TransactionTypeEnum type) {
        return transactions
                .stream()
                .filter(transaction -> type.equals(transaction.type()))
                .map(TransactionDTO::price)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
