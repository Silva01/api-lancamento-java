package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.List;
import java.util.Objects;

public class GetInformationAccountUseCase implements UseCase<AccountOutput> {

    private final Repository<Account> repository;
    private final Repository<List<Transaction>> transactionRepository;
    private final GenericResponseMapper factory;

    private static final int NUMBER_OF_TRANSACTIONS = 10;

    public GetInformationAccountUseCase(Repository<Account> repository, Repository<List<Transaction>> transactionRepository, GenericResponseMapper factory) {
        this.repository = repository;
        this.transactionRepository = transactionRepository;
        this.factory = factory;
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var cpf = (ICpfParam) param.input();
        var accountDto = repository.exec(cpf.cpf()).build();
        var transactions = transactionRepository.exec(cpf.cpf(), NUMBER_OF_TRANSACTIONS);
        CreditCard creditCard = null;

        if (Objects.nonNull(accountDto.creditCard())) {
            creditCard = new CreditCard(accountDto.creditCard().number(), accountDto.creditCard().cvv(), accountDto.creditCard().flag(), accountDto.creditCard().balance(), accountDto.creditCard().expirationDate());
        }

        var account = new Account(accountDto.number(), accountDto.agency(), accountDto.balance(), accountDto.password(), accountDto.active(), accountDto.cpf(), creditCard, transactions);
        factory.fillIn(account.build(), param.output());
        return AccountBuilder.buildFullAccountOutput().createFrom(account.build());
    }
}
