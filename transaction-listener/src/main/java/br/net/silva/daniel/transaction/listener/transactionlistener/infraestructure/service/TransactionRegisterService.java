package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.service;

import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.RegisterResponse;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.TransactionResponse;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.validation.Validation;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.enuns.ResponseStatus;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionRegisterService {

    private final Validation<Optional<Account>> accountValidation;
    private final AccountRepository repository;

    public RegisterResponse registerTransaction(BatchTransactionInput message) {
        final var sourceAccount = repository.findByAccountNumberAndAgencyAndCpf(message.sourceAccount().accountNumber(), message.sourceAccount().agency(), message.sourceAccount().cpf());
        final var destinyAccount = repository.findByAccountNumberAndAgencyAndCpf(message.destinyAccount().accountNumber(), message.destinyAccount().agency(), message.destinyAccount().cpf());

        try {
            accountValidation.validate(sourceAccount);
            accountValidation.validate(destinyAccount);

            return new RegisterResponse(
                    ResponseStatus.SUCCESS,
                    message.sourceAccount().accountNumber(),
                    message.sourceAccount().agency(),
                    message.destinyAccount().accountNumber(),
                    message.destinyAccount().agency(),
                    List.of(new TransactionResponse(message.batchTransaction().get(0).id(), message.batchTransaction().get(0).idempotencyId())),
                    "Success"
            );
        } catch (GenericException e) {
            return new RegisterResponse(
                    ResponseStatus.ERROR,
                    message.sourceAccount().accountNumber(),
                    message.sourceAccount().agency(),
                    message.destinyAccount().accountNumber(),
                    message.destinyAccount().agency(),
                    List.of(new TransactionResponse(message.batchTransaction().get(0).id(), message.batchTransaction().get(0).idempotencyId())),
                    e.getMessage()
            );
        }


    }
}
