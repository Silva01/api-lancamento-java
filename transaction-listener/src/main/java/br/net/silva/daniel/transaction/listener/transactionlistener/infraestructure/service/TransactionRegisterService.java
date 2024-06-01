package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.service;

import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.RegisterResponse;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.TransactionResponse;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.enuns.ResponseStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionRegisterService {
    public RegisterResponse registerTransaction(BatchTransactionInput message) {
        return new RegisterResponse(
                ResponseStatus.SUCCESS,
                message.sourceAccount().accountNumber(),
                message.sourceAccount().agency(),
                message.destinyAccount().accountNumber(),
                message.destinyAccount().agency(),
                List.of(new TransactionResponse(message.batchTransaction().get(0).id(), message.batchTransaction().get(0).idempotencyId()))
        );
    }
}
