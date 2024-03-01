package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.CreditCardNumberDifferentException;
import br.net.silva.business.value_object.input.DeactivateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.value_object.Source;

public class DeactivateCreditCardUseCase implements UseCase<EmptyOutput> {

    private final Repository<AccountOutput> findAccountRepository;
    private final Repository<AccountOutput> saveAccountRepository;

    private final IFactoryAggregate<Account, AccountDTO> accountFactory;

    public DeactivateCreditCardUseCase(Repository<AccountOutput> findAccountRepository, Repository<AccountOutput> saveAccountRepository) {
        this.findAccountRepository = findAccountRepository;
        this.saveAccountRepository = saveAccountRepository;
        this.accountFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        var input = (DeactivateCreditCardInput) param.input();
        var accountOutput = findAccountRepository.exec(input.accountNumber(), input.agency(), input.cpf());

        var account = accountFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));

        if (!account.build().creditCard().number().equals(input.creditCardNumber())) {
            throw new CreditCardNumberDifferentException("Credit card number is different the credit card informed for the user");
        }

        account.deactivateCreditCard();
        saveAccountRepository.exec(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));

        return EmptyOutput.INSTANCE;
    }
}
