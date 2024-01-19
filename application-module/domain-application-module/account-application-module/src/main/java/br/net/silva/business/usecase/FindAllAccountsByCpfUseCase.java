package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.FindAccountDTO;
import br.net.silva.business.value_object.output.AccountResponseDto;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.List;

public class FindAllAccountsByCpfUseCase implements UseCase<List<AccountDTO>> {

    private final Repository<List<Account>> repository;

    public FindAllAccountsByCpfUseCase(Repository<List<Account>> repository) {
        this.repository = repository;
    }

    @Override
    public List<AccountDTO> exec(Source param) throws GenericException {
        var findAccountDto = (FindAccountDTO) param.input();
        var accounts = repository.exec(findAccountDto.cpf());
        var dtoList = accounts.stream().map(Account::build).toList();

        ((AccountResponseDto) param.output()).getAccounts().addAll(dtoList);
        return dtoList;
    }
}
