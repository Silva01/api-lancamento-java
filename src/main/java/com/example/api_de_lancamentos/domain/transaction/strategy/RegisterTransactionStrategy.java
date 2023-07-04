package com.example.api_de_lancamentos.domain.transaction.strategy;

import com.example.api_de_lancamentos.domain.account.entity.Account;
import com.example.api_de_lancamentos.domain.shared.interfaces.PolicyStrategy;
import com.example.api_de_lancamentos.domain.shared.interfaces.TransactionPolicy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RegisterTransactionStrategy extends PolicyStrategy<Account, BigDecimal> {

    private static final int BALANCE_CREDIT = 0;
    private static final int BALANCE_DEBIT = 1;
    public RegisterTransactionStrategy(List<TransactionPolicy<BigDecimal>> transactionPolicies) {
        super(transactionPolicies);
    }

    @Override
    public Account execute(Account entity) {
        List<BigDecimal> responseCal = new ArrayList<>();
        for (TransactionPolicy<BigDecimal> policy : super.policies) {
            policy.addAccount(entity);
            responseCal.add(policy.executeTransaction());
        }

        return new Account(entity.getAccountNumber(), entity.getAccountName(), responseCal.get(BALANCE_CREDIT), responseCal.get(BALANCE_DEBIT));
    }
}
