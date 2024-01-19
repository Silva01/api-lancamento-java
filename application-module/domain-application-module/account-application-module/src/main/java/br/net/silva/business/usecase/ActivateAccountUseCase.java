package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.EmptyOutput;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class ActivateAccountUseCase implements UseCase<AccountDTO> {

    private final Repository<Account> activateAccountRepository;
    private final Repository<Account> findAccountRepository;

    public ActivateAccountUseCase(Repository<Account> activateAccountRepository, Repository<Account> findAccountRepository) {
        this.activateAccountRepository = activateAccountRepository;
        this.findAccountRepository = findAccountRepository;
    }

    @Override
    public AccountDTO exec(Source param) throws GenericException {
        try {
            var dto = (ActivateAccount) param.input();
            var account = findAccountRepository.exec(dto.accountNumber(), dto.agency(), dto.cpf());
            account.activate();
            return activateAccountRepository.exec(account).build();
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
