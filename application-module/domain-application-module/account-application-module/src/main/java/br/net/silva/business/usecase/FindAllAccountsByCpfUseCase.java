package br.net.silva.business.usecase;

import br.net.silva.business.value_object.output.AccountResponseDto;
import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.mapper.MapToFindAccountMapper;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.List;

public class FindAllAccountsByCpfUseCase implements UseCase {

    private final Repository<List<Account>> repository;
    private final MapToFindAccountMapper mapper;

    public FindAllAccountsByCpfUseCase(Repository<List<Account>> repository) {
        this.repository = repository;
        this.mapper = MapToFindAccountMapper.INSTANCE;
    }

    @Override
    public void exec(Source param) throws GenericException {
        var findAccountDto = this.mapper.mapToFindAccountDto(param.input());
        var accounts = repository.exec(findAccountDto.cpf());
        param.map().put(TypeAccountMapperEnum.ACCOUNT.name(), new AccountResponseDto(accounts.stream().map(Account::build).toList()));
    }
}
