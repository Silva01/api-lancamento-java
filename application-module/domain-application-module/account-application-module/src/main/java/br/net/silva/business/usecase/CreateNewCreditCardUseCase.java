package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.CreateCreditCardInput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class CreateNewCreditCardUseCase implements UseCase<AccountDTO> {

    private final Repository<Account> findAccountByCpfAndAgencyAndAccountNumberRepository;
    private final Repository<Account> saveAccountRepository;

    public CreateNewCreditCardUseCase(Repository<Account> findAccountByCpfAndAgencyAndAccountNumberRepository, Repository<Account> saveAccountRepository) {
        this.findAccountByCpfAndAgencyAndAccountNumberRepository = findAccountByCpfAndAgencyAndAccountNumberRepository;
        this.saveAccountRepository = saveAccountRepository;
    }

    @Override
    public AccountDTO exec(Source param) throws GenericException {
        try {
            var newCreditCardInput = (CreateCreditCardInput) param.input();
            var creditCard = new CreditCard();

            var account = findAccountByCpfAndAgencyAndAccountNumberRepository.exec(
                    newCreditCardInput.cpf(), newCreditCardInput.agency(), newCreditCardInput.accountNumber());

            account.vinculateCreditCard(creditCard);
            return saveAccountRepository.exec(account).build();
        } catch (Exception e) {
            throw new GenericException("Generic error", e);
        }
    }
}
