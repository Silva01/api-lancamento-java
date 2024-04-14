package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.IGenericBuilder;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.List;

public class FindAllAccountsByCpfUseCase implements UseCase<List<AccountOutput>> {

    private final FindApplicationBaseGateway<AccountOutput> findGateway;
    private final GenericResponseMapper factory;
    private final IGenericBuilder<AccountOutput, AccountDTO> accountBuilder;

    public FindAllAccountsByCpfUseCase(FindApplicationBaseGateway<AccountOutput> findGateway, GenericResponseMapper factory) {
        this.findGateway = findGateway;
        this.factory = factory;
        this.accountBuilder = AccountBuilder.buildFullAccountOutput();
    }

    @Override
    public List<AccountOutput> exec(Source param) throws GenericException {
        var findAccountDto = (ICpfParam) param.input();
        var outputAccounts = findGateway.findAllByCpf(findAccountDto);
        var dtoList = outputAccounts.stream().map(AccountBuilder.buildFullAccountDto()::createFrom).toList();

        factory.fillIn(dtoList, param.output());
        return dtoList.stream().map(accountBuilder::createFrom).toList();
    }
}
