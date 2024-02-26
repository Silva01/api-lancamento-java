package br.net.silva.business.usecase;

import br.net.silva.business.build.CreditCardBuilder;
import br.net.silva.business.build.TransactionBuilder;
import br.net.silva.business.factory.AccountOutputFactory;
import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class CreateNewCreditCardUseCase implements UseCase<AccountOutput> {

    private final Repository<Account> findAccountByCpfAndAgencyAndAccountNumberRepository;
    private final Repository<Account> saveAccountRepository;

    public CreateNewCreditCardUseCase(Repository<Account> findAccountByCpfAndAgencyAndAccountNumberRepository, Repository<Account> saveAccountRepository) {
        this.findAccountByCpfAndAgencyAndAccountNumberRepository = findAccountByCpfAndAgencyAndAccountNumberRepository;
        this.saveAccountRepository = saveAccountRepository;
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        try {
            var newCreditCardInput = (CreateCreditCardInput) param.input();
            var creditCard = new CreditCard();

            var account = findAccountByCpfAndAgencyAndAccountNumberRepository.exec(
                    newCreditCardInput.agency(), newCreditCardInput.accountNumber(), newCreditCardInput.cpf());

            account.vinculateCreditCard(creditCard);
            var newAccountDto = saveAccountRepository.exec(account).build();

            return AccountOutputFactory.createOutput()
                    .withNumber(newAccountDto.number())
                    .withAgency(newAccountDto.agency())
                    .withBalance(newAccountDto.balance())
                    .withPassword(newAccountDto.password())
                    .withFlagActive(newAccountDto.active())
                    .withCpf(newAccountDto.cpf())
                    .withTransactions(TransactionBuilder.buildFullTransactionsOutput().createFrom(newAccountDto.transactions()))
                    .andWithCreditCard(CreditCardBuilder.buildFullCreditCardOutput().createFrom(newAccountDto.creditCard()))
                    .build();
        } catch (Exception e) {
            throw new GenericException("Generic error", e);
        }
    }
}
