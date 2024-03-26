package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;

import java.util.List;

public class GetInformationAccountUseCase implements UseCase<AccountOutput> {

    private final Repository<AccountOutput> repository;
    private final Repository<List<TransactionOutput>> transactionRepository;
    private final GenericResponseMapper factory;

    private static final int NUMBER_OF_TRANSACTIONS = 10;

    public GetInformationAccountUseCase(Repository<AccountOutput> repository, Repository<List<TransactionOutput>> transactionRepository, GenericResponseMapper factory) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
        this.factory = factory;
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var cpf = (ICpfParam) param.input();
        var accountOutput = repository.exec(cpf.cpf());
        var outputTransactions = transactionRepository.exec(cpf.cpf(), NUMBER_OF_TRANSACTIONS);

        var accountDto = AccountBuilder.buildFullAccountDto().createFrom(AccountBuilder.buildNewAccountFrom(accountOutput, outputTransactions, accountOutput.creditCard()));
        factory.fillIn(accountDto, param.output());
        return AccountBuilder.buildFullAccountOutput().createFrom(accountDto);
    }
}
