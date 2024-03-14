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
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.application.value_object.Source;

import java.util.Collections;
import java.util.List;

public class CreateNewAccountByCpfUseCase implements UseCase<AccountOutput> {

    private final IFactoryAggregate<Account, AccountDTO> createNewAccountByCpfFactory;
    private final FindApplicationBaseGateway<AccountOutput> findIsExistsPeerCPFRepository;
    private final SaveApplicationBaseGateway<AccountOutput> saveRepository;
    private final GenericResponseMapper factory;
    private final IGenericBuilder<List<TransactionOutput>, List<TransactionDTO>> transactionOutputBuilder;
    private final IGenericBuilder<CreditCardOutput, CreditCardDTO> creditCardOutputBuilder;

    public CreateNewAccountByCpfUseCase(ApplicationBaseGateway<AccountOutput> baseRepository, GenericResponseMapper factory) {
        this.findIsExistsPeerCPFRepository = baseRepository;
        this.saveRepository = baseRepository;
        this.factory = factory;
        this.createNewAccountByCpfFactory = new CreateNewAccountByCpfFactory();
        this.transactionOutputBuilder = TransactionBuilder.buildFullTransactionListOutput();
        this.creditCardOutputBuilder = CreditCardBuilder.buildFullCreditCardOutput();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var clientCpf = (ICpfParam) param.input();
        var agencyInterface = (IAgencyParam) param.input();
        if (isExistsAccountActiveForCPF(clientCpf)) {
            throw new AccountExistsForCPFInformatedException("Exists account active for CPF informated");
        }

        var newAccount = new Account(agencyInterface.agency(), "default", clientCpf.cpf());

        var accountOutput = saveRepository.save(AccountBuilder.buildFullAccountOutput().createFrom(newAccount.build()));

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

    private boolean isExistsAccountActiveForCPF(ICpfParam cpf) {
        return findIsExistsPeerCPFRepository.findById(cpf).isPresent();
    }
}
