package br.net.silva.daniel.strategy;

import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.enuns.TransactionTypeEnum;

import java.math.BigDecimal;
import java.util.List;

@FunctionalInterface
public interface ICalculation {
    BigDecimal calculate(List<TransactionDTO> transactions, TransactionTypeEnum type);
}
