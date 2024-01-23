package br.net.silva.business.usecase;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.List;

public class FindAllAccountsByCpfUseCase implements UseCase<List<AccountDTO>> {

    private final Repository<List<Account>> repository;
    private final GenericResponseMapper factory;

    public FindAllAccountsByCpfUseCase(Repository<List<Account>> repository, GenericResponseMapper factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public List<AccountDTO> exec(Source param) throws GenericException {
        var findAccountDto = (ICpfParam) param.input();
        var accounts = repository.exec(findAccountDto.cpf());
        var dtoList = accounts.stream().map(Account::build).toList();

        factory.fillIn(dtoList, param.output());
        return dtoList;
    }
}
