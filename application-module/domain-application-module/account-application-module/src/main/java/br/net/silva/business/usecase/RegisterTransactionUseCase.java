package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.strategy.CalculateStrategy;
import br.net.silva.daniel.shared.application.value_object.Source;

import java.util.List;

public class RegisterTransactionUseCase implements UseCase<EmptyOutput> {

    private final Repository<AccountOutput> findAccountRepository;
    private final Repository<AccountOutput> saveAccountRepository;

    private final IFactoryAggregate<Account, AccountDTO> accountFactory;

    public RegisterTransactionUseCase(Repository<AccountOutput> findAccountRepository, Repository<AccountOutput> saveAccountRepository) {
        this.findAccountRepository = findAccountRepository;
        this.saveAccountRepository = saveAccountRepository;
        this.accountFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        var input = (BatchTransactionInput) param.input();
        var transactionDTO = buildTransactionDTO(input);

        var sourceAccountOutput = findAccountRepository.exec(input.sourceAccount().accountNumber(), input.sourceAccount().agency(), input.sourceAccount().cpf());
        var destinyAccountOutput = findAccountRepository.exec(input.destinyAccount().accountNumber(), input.destinyAccount().agency(), input.destinyAccount().cpf());

        var sourceAccount = accountFactory.create(AccountBuilder.buildFullAccountDto().createFrom(sourceAccountOutput));
        var destinyAccount = accountFactory.create(AccountBuilder.buildFullAccountDto().createFrom(destinyAccountOutput));

        sourceAccount.registerTransaction(transactionDTO, CalculateStrategy.calculationBuy());
        destinyAccount.registerTransaction(transactionDTO, CalculateStrategy.calculationSale());

        saveAccountRepository.exec(AccountBuilder.buildFullAccountOutput().createFrom(sourceAccount.build()));
        saveAccountRepository.exec(AccountBuilder.buildFullAccountOutput().createFrom(destinyAccount.build()));

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
