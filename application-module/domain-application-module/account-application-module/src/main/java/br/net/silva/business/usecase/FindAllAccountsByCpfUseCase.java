package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.daniel.interfaces.IGenericBuilder;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.List;

public class FindAllAccountsByCpfUseCase implements UseCase<List<AccountOutput>> {

    private final Repository<List<AccountOutput>> repository;
    private final GenericResponseMapper factory;
    private final IGenericBuilder<AccountOutput, AccountDTO> accountBuilder;

    public FindAllAccountsByCpfUseCase(Repository<List<AccountOutput>> repository, GenericResponseMapper factory) {
        this.repository = repository;
        this.factory = factory;
        this.accountBuilder = AccountBuilder.buildFullAccountOutput();
    }

    @Override
    public List<AccountOutput> exec(Source param) throws GenericException {
        var findAccountDto = (ICpfParam) param.input();
        var outputAccounts = repository.exec(findAccountDto.cpf());
        var dtoList = outputAccounts.stream().map(AccountBuilder.buildFullAccountDto()::createFrom).toList();

        factory.fillIn(dtoList, param.output());
        return dtoList.stream().map(accountBuilder::createFrom).toList();
    }
}
