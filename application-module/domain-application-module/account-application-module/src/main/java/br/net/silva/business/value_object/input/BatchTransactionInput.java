package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.IAccountParam;

import java.math.BigDecimal;
import java.util.List;

public record BatchTransactionInput(
        AccountInput sourceAccount,
        AccountInput destinyAccount,
        List<TransactionInput> batchTransaction
) implements IAccountParam {

    public BigDecimal calculateTotal() {
        return batchTransaction.stream().map(TransactionInput::price).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    @Override
    public Integer accountNumber() {
        return sourceAccount().accountNumber();
    }

    @Override
    public Integer agency() {
        return sourceAccount().agency();
    }

    @Override
    public String cpf() {
        return sourceAccount().cpf();
    }

    public boolean hasTransactionDuplicated() {
        final var quantityOfIdempotency = batchTransaction.stream().map(TransactionInput::idempotencyId).distinct().count();
        return quantityOfIdempotency != batchTransaction.size();
    }
}
