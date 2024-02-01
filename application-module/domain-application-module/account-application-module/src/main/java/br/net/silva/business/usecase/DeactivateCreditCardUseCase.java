package br.net.silva.business.usecase;

import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class DeactivateCreditCardUseCase implements UseCase<EmptyOutput> {

    private final Repository<Account> findAccountRepository;
    private final Repository<Account> saveAccountRepository;

    public DeactivateCreditCardUseCase(Repository<Account> findAccountRepository, Repository<Account> saveAccountRepository) {
        this.findAccountRepository = findAccountRepository;
        this.saveAccountRepository = saveAccountRepository;
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        var input = (DeactivateCreditCardInput) param.input();
        var account = findAccountRepository.exec(input.accountNumber(), input.agency(), input.cpf());

        if (!account.build().creditCard().number().equals(input.creditCardNumber())) {
            throw new CreditCardNumberDifferentException("Credit card number is different the credit card informed for the user");
        }

        account.deactivateCreditCard();
        saveAccountRepository.exec(account).build();

        return EmptyOutput.INSTANCE;
    }
}
