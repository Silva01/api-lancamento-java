package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.FindAccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.AccountNotExistsException;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;

import java.util.Optional;

public class FindAccountUseCase implements UseCase<FindAccountDTO, AccountDTO> {

    private final Repository<Optional<Account>> findAccountRepository;

    public FindAccountUseCase(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }

    @Override
    public AccountDTO exec(FindAccountDTO param) throws GenericException {
        var accountOptional = findAccountRepository.exec(param.accountNumber(), param.agency());
        var account = accountOptional.orElseThrow(() -> new AccountNotExistsException("Account not found"));
        return account.create();
    }
}
