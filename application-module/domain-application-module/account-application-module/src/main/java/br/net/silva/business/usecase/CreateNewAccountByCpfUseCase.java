package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.build.CreditCardBuilder;
import br.net.silva.daniel.shared.application.interfaces.IGenericBuilder;
import br.net.silva.business.build.TransactionBuilder;
import br.net.silva.business.exception.AccountExistsForCPFInformatedException;
import br.net.silva.business.factory.AccountOutputFactory;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.business.value_object.output.CreditCardOutput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.CreditCardDTO;
import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.factory.CreateNewAccountByCpfFactory;
import br.net.silva.daniel.shared.application.interfaces.IAgencyParam;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.application.value_object.Source;

import java.util.Collections;
import java.util.List;

public class CreateNewAccountByCpfUseCase implements UseCase<AccountOutput> {

    private final IFactoryAggregate<Account, AccountDTO> createNewAccountByCpfFactory;
    private final Repository<Boolean> findIsExistsPeerCPFRepository;
    private final Repository<AccountOutput> saveRepository;
    private final GenericResponseMapper factory;
    private final IGenericBuilder<List<TransactionOutput>, List<TransactionDTO>> transactionOutputBuilder;
    private final IGenericBuilder<CreditCardOutput, CreditCardDTO> creditCardOutputBuilder;

    public CreateNewAccountByCpfUseCase(Repository<Boolean> findIsExistsPeerCPFRepository, Repository<AccountOutput> saveRepository, GenericResponseMapper factory) {
        this.findIsExistsPeerCPFRepository = findIsExistsPeerCPFRepository;
        this.saveRepository = saveRepository;
        this.factory = factory;
        this.createNewAccountByCpfFactory = new CreateNewAccountByCpfFactory();
        this.transactionOutputBuilder = TransactionBuilder.buildFullTransactionListOutput();
        this.creditCardOutputBuilder = CreditCardBuilder.buildFullCreditCardOutput();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var clientCpf = (ICpfParam) param.input();
        var agencyInterface = (IAgencyParam) param.input();
        if (isExistsAccountActiveForCPF(clientCpf.cpf())) {
            throw new AccountExistsForCPFInformatedException("Exists account active for CPF informated");
        }

        var accountDtoParam = new AccountDTO(
                null,
                agencyInterface.agency(),
                null,
                "default",
                true,
                clientCpf.cpf(),
                Collections.emptyList(),
                null);

        var accountOutput = saveRepository.exec(AccountBuilder.buildFullAccountOutput().createFrom(accountDtoParam));

        var accountAggregate = createNewAccountByCpfFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));

        factory.fillIn(accountAggregate.build(), param.output());
        var accountDto = accountAggregate.build();
        return AccountOutputFactory
                .createOutput()
                .withNumber(accountDto.number())
                .withAgency(accountDto.agency())
                .withBalance(accountDto.balance())
                .withPassword(accountDto.password())
                .withFlagActive(accountDto.active())
                .withCpf(accountDto.cpf())
                .withTransactions(transactionOutputBuilder.createFrom(accountDto.transactions()))
                .andWithCreditCard(creditCardOutputBuilder.createFrom(accountDto.creditCard()))
                .build();

    }

    private boolean isExistsAccountActiveForCPF(String cpf) {
        return findIsExistsPeerCPFRepository.exec(cpf);
    }
}
