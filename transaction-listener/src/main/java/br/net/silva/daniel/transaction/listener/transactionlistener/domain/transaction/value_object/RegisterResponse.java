package br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object;

import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.enuns.ResponseStatus;

import java.util.List;

public record RegisterResponse(
        ResponseStatus status,
        Integer sourceAccountNumber,
        Integer sourceAccountAgency,
        Integer destinyAccountNumber,
        Integer destinyAccountAgency,
        List<TransactionResponse> transactions,
        String message
) {
}
