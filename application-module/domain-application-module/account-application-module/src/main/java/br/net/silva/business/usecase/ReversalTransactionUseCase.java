package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.strategy.CalculateStrategy;
import br.net.silva.daniel.shared.application.value_object.Source;

import java.util.List;

public class ReversalTransactionUseCase implements UseCase<EmptyOutput> {

    private final Repository<TransactionOutput> findTransactionByIdAndIdempotencyIdRepository;
    private final Repository<AccountOutput> findAccountByAccountNumberRepository;
    private final Repository<AccountOutput> saveAccountRepository;
    private final IFactoryAggregate<Account, AccountDTO> accountFactory;

    public ReversalTransactionUseCase(Repository<TransactionOutput> findTransactionByIdAndIdempotencyIdRepository, Repository<AccountOutput> findAccountByAccountNumberRepository, Repository<AccountOutput> saveAccountRepository) {
        this.findTransactionByIdAndIdempotencyIdRepository = findTransactionByIdAndIdempotencyIdRepository;
        this.findAccountByAccountNumberRepository = findAccountByAccountNumberRepository;
        this.saveAccountRepository = saveAccountRepository;
        this.accountFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        var input = (ReversalTransactionInput) param.input();
        var transaction = findTransactionByIdAndIdempotencyIdRepository.exec(input.id(), input.idempotencyId());
        var accountOutput = findAccountByAccountNumberRepository.exec(transaction.originAccountNumber());

        var reversalTransaction = new Transaction(
                transaction.id(),
                transaction.description(),
                transaction.price().negate(),
                transaction.quantity(),
                TransactionTypeEnum.REVERSAL,
                transaction.originAccountNumber(),
                transaction.destinationAccountNumber(),
                transaction.idempotencyId(),
                transaction.creditCardNumber(),
                transaction.creditCardCvv()
        );

        var account = accountFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));
        account.registerTransaction(List.of(reversalTransaction.build()), CalculateStrategy.calculationBuy());

        saveAccountRepository.exec(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));

        return EmptyOutput.INSTANCE;
    }
}
