package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.strategy.CalculateStrategy;
import br.net.silva.daniel.value_object.Source;

import java.util.List;

public class ReversalTransactionUseCase implements UseCase<EmptyOutput> {

    private final Repository<Transaction> findTransactionByIdAndIdempotencyIdRepository;
    private final Repository<Account> findAccountByAccountNumberRepository;

    private final Repository<Account> saveAccountRepository;

    public ReversalTransactionUseCase(Repository<Transaction> findTransactionByIdAndIdempotencyIdRepository, Repository<Account> findAccountByAccountNumberRepository, Repository<Account> saveAccountRepository) {
        this.findTransactionByIdAndIdempotencyIdRepository = findTransactionByIdAndIdempotencyIdRepository;
        this.findAccountByAccountNumberRepository = findAccountByAccountNumberRepository;
        this.saveAccountRepository = saveAccountRepository;
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        var input = (ReversalTransactionInput) param.input();
        var transaction = findTransactionByIdAndIdempotencyIdRepository.exec(input.id(), input.idempotencyId());
        var account = findAccountByAccountNumberRepository.exec(transaction.build().originAccountNumber());

        var originTransaction = transaction.build();
        var reversalTransaction = new Transaction(
                originTransaction.id(),
                originTransaction.description(),
                originTransaction.price().negate(),
                originTransaction.quantity(),
                TransactionTypeEnum.REVERSAL,
                originTransaction.originAccountNumber(),
                originTransaction.destinationAccountNumber(),
                originTransaction.idempotencyId(),
                originTransaction.creditCardNumber(),
                originTransaction.creditCardCvv()
        );

        account.registerTransaction(List.of(reversalTransaction.build()), CalculateStrategy.calculationBuy());

        saveAccountRepository.exec(account);

        return EmptyOutput.INSTANCE;
    }
}
