package br.net.silva.daniel.usecase;

import br.net.silva.daniel.SharedParamDelegate;
import br.net.silva.daniel.dto.FindAccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.AccountNotExistsException;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IProcessResponse;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;

import java.util.Optional;

public class FindAccountUseCase implements UseCase<SharedParamDelegate, IProcessResponse> {

    private final Repository<Optional<Account>> findAccountRepository;

    public FindAccountUseCase(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
    }

    @Override
    public IProcessResponse exec(SharedParamDelegate param) throws GenericException {
        var findAccountDTO = (FindAccountDTO) param.getParam();
        var accountOptional = findAccountRepository.exec(findAccountDTO.accountNumber(), findAccountDTO.agency());
        var account = accountOptional.orElseThrow(() -> new AccountNotExistsException("Account not found"));
        param.addResponse(account);
        return param;
    }
}
