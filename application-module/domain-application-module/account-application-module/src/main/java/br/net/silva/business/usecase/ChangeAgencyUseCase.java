package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.ChangeAgencyInput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.value_object.Source;

public class ChangeAgencyUseCase implements UseCase<AccountDTO> {

    private final Repository<Account> findAccountByCpfAndAccountNumberRepository;
    private final Repository<Account> saveAccountRepository;

    private final IFactoryAggregate<Account, AccountDTO> factoryAggregate;

    public ChangeAgencyUseCase(Repository<Account> findAccountByCpfAndAccountNumberRepository, Repository<Account> saveAccountRepository) {
        this.findAccountByCpfAndAccountNumberRepository = findAccountByCpfAndAccountNumberRepository;
        this.saveAccountRepository = saveAccountRepository;
        this.factoryAggregate = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public AccountDTO exec(Source param) throws GenericException {
        try {
            var changeAgencyInput = (ChangeAgencyInput) param.input();
            var account = findAccountByCpfAndAccountNumberRepository.exec(changeAgencyInput.cpf(), changeAgencyInput.accountNumber(), changeAgencyInput.oldAgencyNumber());

            var accountDto = account.build();

            var newAccountDto = new AccountDTO(
                    accountDto.number(),
                    changeAgencyInput.newAgencyNumber(),
                    accountDto.balance(),
                    accountDto.password(),
                    accountDto.active(),
                    accountDto.cpf(),
                    accountDto.transactions(),
                    accountDto.creditCard()
            );

            account.deactivate();
            saveAccountRepository.exec(account);

            var newAccount = factoryAggregate.create(newAccountDto);
            saveAccountRepository.exec(newAccount);

            return newAccountDto;
        } catch (Exception e) {
            throw new GenericException("Generic error", e);
        }
    }
}
