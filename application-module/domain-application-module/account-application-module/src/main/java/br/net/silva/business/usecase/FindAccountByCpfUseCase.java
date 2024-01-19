package br.net.silva.business.usecase;

import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.interfaces.IAccountParam;
import br.net.silva.business.mapper.MapToFindAccountMapper;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class FindAccountByCpfUseCase implements UseCase<AccountDTO> {

    private final Repository<Optional<Account>> findActiveAccountRepository;
    private final MapToFindAccountMapper mapper;

    public FindAccountByCpfUseCase(Repository<Optional<Account>> findActiveAccountRepository) {
        this.findActiveAccountRepository = findActiveAccountRepository;
        this.mapper = MapToFindAccountMapper.INSTANCE;
    }

    @Override
    public AccountDTO exec(Source param) throws GenericException {
        var findAccountDto = (IAccountParam) param.input();
        var accountOptional = findActiveAccountRepository.exec(findAccountDto.cpf());
        var accountAggregate =  accountOptional.orElseThrow(() -> new AccountNotExistsException("Account not found"));
        //TODO: aqui precisa implementar um mapper para preenchimento do objeto de retorno

        return accountAggregate.build();
    }
}
