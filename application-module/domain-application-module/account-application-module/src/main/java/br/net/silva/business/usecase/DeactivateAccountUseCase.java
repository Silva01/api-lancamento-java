package br.net.silva.business.usecase;

import br.net.silva.daniel.interfaces.IAccountParam;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class DeactivateAccountUseCase implements UseCase<AccountDTO> {

    private final Repository<Account> deactivateAccountRepository;
    public DeactivateAccountUseCase(Repository<Account> deactivateAccountRepository) {
        this.deactivateAccountRepository = deactivateAccountRepository;
    }

    @Override
    public AccountDTO exec(Source param) throws GenericException {
        try {
            var dto = (IAccountParam) param.input();
            var accountAggregate =  deactivateAccountRepository.exec(dto.cpf());
            return accountAggregate.build();
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
