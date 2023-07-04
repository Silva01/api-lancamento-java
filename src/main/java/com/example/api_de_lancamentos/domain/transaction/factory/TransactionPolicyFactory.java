package com.example.api_de_lancamentos.domain.transaction.factory;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.shared.interfaces.TransactionPolicy;
import com.example.api_de_lancamentos.domain.transaction.entity.Transaction;
import com.example.api_de_lancamentos.domain.transaction.enuns.TypeTransactionEnum;
import com.example.api_de_lancamentos.domain.transaction.exception.TransactionNotValidException;
import com.example.api_de_lancamentos.domain.transaction.policy.CreditTransactionPolicy;
import com.example.api_de_lancamentos.domain.transaction.policy.DebitTransactionPolicy;

import java.math.BigDecimal;
import java.util.List;

public class TransactionPolicyFactory {

    public TransactionPolicy getTransactionPolicy(String policyType, Account account, List<Transaction> transactions) {
        if (policyType == null) {
            throw new TransactionNotValidException("Tipo de Policy não pode ser nulo");
        }
        if (policyType.equalsIgnoreCase("CREDIT")) {
            return new CreditTransactionPolicy(account, transactions.stream().filter(t -> t.getType().equals(TypeTransactionEnum.CREDIT)).map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add));
        } else if (policyType.equalsIgnoreCase("DEBIT")) {
            return new DebitTransactionPolicy(account, transactions.stream().filter(t -> t.getType().equals(TypeTransactionEnum.DEBIT)).map(Transaction::getValue).reduce(BigDecimal.ZERO, BigDecimal::add));
        }

        throw new TransactionNotValidException("Tipo de Policy não encontrado");
    }
}
