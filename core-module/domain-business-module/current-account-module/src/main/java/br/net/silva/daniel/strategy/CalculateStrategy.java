package br.net.silva.daniel.strategy;

import br.net.silva.daniel.dto.TransactionDTO;

import java.math.BigDecimal;

public class CalculateStrategy {

    public static ICalculation calculationSale() {
        return transactions -> transactions.stream().map(TransactionDTO::price).reduce(BigDecimal.ZERO, BigDecimal::add).multiply(BigDecimal.valueOf(-1));
    }

    public static ICalculation calculationBuy() {
        return transactions -> transactions.stream().map(TransactionDTO::price).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
