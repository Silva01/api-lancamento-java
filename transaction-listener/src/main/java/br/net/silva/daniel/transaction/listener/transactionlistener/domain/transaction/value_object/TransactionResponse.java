package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object;

public record TransactionResponse(
        Long id,
        Long idempotency
) {
}
