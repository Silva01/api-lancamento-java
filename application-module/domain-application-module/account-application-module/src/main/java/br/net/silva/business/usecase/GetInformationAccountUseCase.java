package br.net.silva.business.usecase;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class GetInformationAccountUseCase implements UseCase<AccountDTO> {

    private final Repository<Account> repository;
    private final GenericResponseMapper factory;

    public GetInformationAccountUseCase(Repository<Account> repository, GenericResponseMapper factory) {
        this.repository = repository;
        this.factory = factory;
    }

    @Override
    public AccountDTO exec(Source param) throws GenericException {
        var cpf = (ICpfParam) param.input();
        var account = repository.exec(cpf.cpf());
        factory.fillIn(account.build(), param.output());
        return account.build();
    }
}
