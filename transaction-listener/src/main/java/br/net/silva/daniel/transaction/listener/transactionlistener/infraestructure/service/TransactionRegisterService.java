package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.service;

import br.net.silva.business.exception.TransactionDuplicateException;
import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.validation.Validation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.AccountConfiguration;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.RegisterResponse;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.TransactionResponse;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.enuns.ResponseStatus;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionRegisterService {

    private final AccountRepository repository;

    public RegisterResponse registerTransaction(BatchTransactionInput message) {
        final var sourceAccount = repository.findByAccountNumberAndAgencyAndCpf(message.sourceAccount().accountNumber(), message.sourceAccount().agency(), message.sourceAccount().cpf());
        final var destinyAccount = repository.findByAccountNumberAndAgencyAndCpf(message.destinyAccount().accountNumber(), message.destinyAccount().agency(), message.destinyAccount().cpf());

        try {
            validateAccount(message.calculateTotal(), message.sourceAccount(), sourceAccount,  AccountConfiguration.sourceAccountConfiguration());
            validateAccount(message.calculateTotal(), message.destinyAccount(), destinyAccount, AccountConfiguration.destinyAccountConfiguration());

            validateDuplicatedTransaction(message);

            calculateSourceAccountBalance(sourceAccount.get(), message.calculateTotal());

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

    private void calculateSourceAccountBalance(Account account, BigDecimal totalTransaction) {
        account.setBalance(account.getBalance().subtract(totalTransaction));
        repository.save(account);
    }

    private static void validateAccount(BigDecimal totalTransaction, AccountInput accountInput, Optional<Account> accountOpt, AccountConfiguration configuration) throws GenericException {
        final var validation = Validation.buildValidation(totalTransaction, accountInput.toString())
                        .validateIfAccountExists(accountOpt)
                        .validateIfAccountIsActive();

        if (configuration.isValidateBalance()) {
            validation.validateIfBalanceIsSufficient();
        }
    }

    private void validateDuplicatedTransaction(BatchTransactionInput message) throws GenericException {
        final var quantityOfIdempotency = message.batchTransaction().stream().map(TransactionInput::idempotencyId).distinct().count();
        if (message.batchTransaction().size() != quantityOfIdempotency) {
            throw new TransactionDuplicateException("Transaction has 2 or more equals transactions.");
        }
    }
}
