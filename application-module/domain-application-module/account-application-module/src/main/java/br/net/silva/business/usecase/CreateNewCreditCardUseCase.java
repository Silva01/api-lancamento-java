package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.application.value_object.Source;

public class CreateNewCreditCardUseCase implements UseCase<AccountOutput> {

    private final Repository<AccountOutput> findAccountByCpfAndAgencyAndAccountNumberRepository;
    private final Repository<AccountOutput> saveAccountRepository;

    private final IFactoryAggregate<Account, AccountDTO> aggregateFactory;

    public CreateNewCreditCardUseCase(Repository<AccountOutput> findAccountByCpfAndAgencyAndAccountNumberRepository, Repository<AccountOutput> saveAccountRepository) {
        this.findAccountByCpfAndAgencyAndAccountNumberRepository = findAccountByCpfAndAgencyAndAccountNumberRepository;
        this.saveAccountRepository = saveAccountRepository;
        this.aggregateFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        try {
            var newCreditCardInput = (CreateCreditCardInput) param.input();
            var creditCard = new CreditCard();

            var accountOutput = findAccountByCpfAndAgencyAndAccountNumberRepository.exec(
                    newCreditCardInput.agency(), newCreditCardInput.accountNumber(), newCreditCardInput.cpf());

            var account = aggregateFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));

            account.vinculateCreditCard(creditCard);
            return saveAccountRepository.exec(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));
        } catch (Exception e) {
            throw new GenericException("Generic error", e);
        }
    }
}
