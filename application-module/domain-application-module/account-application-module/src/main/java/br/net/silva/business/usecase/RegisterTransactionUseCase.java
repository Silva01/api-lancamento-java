package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.strategy.CalculateStrategy;
import br.net.silva.daniel.value_object.Source;

import java.util.List;

public class RegisterTransactionUseCase implements UseCase<EmptyOutput> {

    private final Repository<Account> findAccountRepository;
    private final Repository<Account> saveAccountRepository;

    public RegisterTransactionUseCase(Repository<Account> findAccountRepository, Repository<Account> saveAccountRepository) {
        this.findAccountRepository = findAccountRepository;
        this.saveAccountRepository = saveAccountRepository;
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        var input = (BatchTransactionInput) param.input();
        var transactionDTO = buildTransactionDTO(input);

        var sourceAccount = findAccountRepository.exec(input.sourceAccount().accountNumber(), input.sourceAccount().agency(), input.sourceAccount().cpf());
        var destinyAccount = findAccountRepository.exec(input.destinyAccount().accountNumber(), input.destinyAccount().agency(), input.destinyAccount().cpf());

        sourceAccount.registerTransaction(transactionDTO, CalculateStrategy.calculationBuy());
        destinyAccount.registerTransaction(transactionDTO, CalculateStrategy.calculationSale());

        saveAccountRepository.exec(sourceAccount);
        saveAccountRepository.exec(destinyAccount);

        return EmptyOutput.INSTANCE;
    }

    private List<TransactionDTO> buildTransactionDTO(BatchTransactionInput batchTransaction) {
        return batchTransaction.batchTransaction()
                .stream()
                .map(transaction -> buildTransactionDTO(transaction, batchTransaction.sourceAccount().accountNumber(), batchTransaction.destinyAccount().accountNumber()))
                .toList();

    }

    private TransactionDTO buildTransactionDTO(TransactionInput transactionInput, Integer sourceAccount, Integer destinyAccount) {
        return new TransactionDTO(
                transactionInput.id(),
                transactionInput.description(),
                transactionInput.price(),
                transactionInput.quantity(),
                transactionInput.type(),
                sourceAccount,
                destinyAccount,
                transactionInput.idempotencyId(),
                transactionInput.creditCardNumber(),
                transactionInput.creditCardCvv());
    }
}
