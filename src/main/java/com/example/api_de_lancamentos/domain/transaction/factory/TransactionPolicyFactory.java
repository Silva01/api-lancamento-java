package com.example.api_de_lancamentos.domain.transaction.factory;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.shared.interfaces.TransactionPolicy;
import com.example.api_de_lancamentos.domain.transaction.policy.CreditTransactionPolicy;
import com.example.api_de_lancamentos.domain.transaction.policy.DebitTransactionPolicy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class TransactionPolicyFactory {

    public static List<TransactionPolicy<BigDecimal>> getTransactionPolicy() {
        return Arrays.asList(new CreditTransactionPolicy(), new DebitTransactionPolicy());
    }
}
