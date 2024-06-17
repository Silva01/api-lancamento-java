package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.service;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate.BaseAccountAggregate;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.RegisterResponse;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.TransactionResponse;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component.ValidationHandler;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.enuns.ResponseStatus;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.factory.ValidatorFactory;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.repository.AccountRepository;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.utils.ExtractorUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionRegisterService {

    private final AccountRepository repository;
    private final ValidationHandler validationHandler;

    public RegisterResponse registerTransaction(BatchTransactionInput message) {
        final var sourceAccount = findAccountFrom(message.sourceAccount());
        final var destinyAccount = findAccountFrom(message.destinyAccount());

        try {
            final var sourceAccountAggregate = ValidatorFactory.createValidatorConfiguratorForSourceAccount(message, sourceAccount);
            final var destinyAccountAggregate = ValidatorFactory.createValidatorConfiguratorForDestinyAccount(message, destinyAccount);
            validationHandler.executeValidations(List.of(sourceAccountAggregate, destinyAccountAggregate));

            //TODO: Ao salvar o saldo calculado, é necessário gravar a transação no banco de dados
            registerTransaction(sourceAccountAggregate);
            registerTransaction(destinyAccountAggregate);

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

    private void registerTransaction(BaseAccountAggregate aggregate) {
        final var account = ExtractorUtils.accountExtractor(aggregate);
        account.setBalance(aggregate.calculateBalance());
        repository.save(account);
    }

    private Optional<Account> findAccountFrom(AccountInput accountInput) {
        return repository.findByAccountNumberAndAgencyAndCpf(accountInput.accountNumber(), accountInput.agency(), accountInput.cpf());
    }
}
