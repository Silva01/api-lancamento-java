package br.net.silva.business.usecase;

import br.net.silva.business.dto.FindAccountDTO;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IGenericPort;
import br.net.silva.daniel.interfaces.IMapper;
import br.net.silva.daniel.interfaces.IProcessResponse;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericMapper;
import br.net.silva.daniel.repository.Repository;

import java.util.Optional;

public class FindAccountUseCase implements UseCase<IProcessResponse<? extends IGenericPort>> {

    private final Repository<Optional<Account>> findAccountRepository;
    private final IMapper<FindAccountDTO, IGenericPort> genericMapper;

    public FindAccountUseCase(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
        this.genericMapper = new GenericMapper<>(FindAccountDTO.class);
    }

    @Override
    public IProcessResponse<?> exec(IGenericPort param) throws GenericException {
        var findAccountDto = genericMapper.map(param);
        var accountOptional = findAccountRepository.exec(findAccountDto.accountNumber(), findAccountDto.agency());
        return accountOptional.orElseThrow(() -> new AccountNotExistsException("Account not found"));
    }
}
