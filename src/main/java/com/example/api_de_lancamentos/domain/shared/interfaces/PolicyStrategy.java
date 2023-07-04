package com.example.api_de_lancamentos.domain.shared.interfaces;

import java.util.List;

public abstract class PolicyStrategy<T, P> {

    protected final List<TransactionPolicy<P>> policies;

    protected PolicyStrategy(List<TransactionPolicy<P>> policies) {
        this.policies = policies;
    }

    public abstract T execute(T entity);
}
