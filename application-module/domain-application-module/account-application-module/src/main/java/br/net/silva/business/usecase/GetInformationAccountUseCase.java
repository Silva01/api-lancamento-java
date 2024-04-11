package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

public class GetInformationAccountUseCase implements UseCase<AccountOutput> {

    private final ApplicationBaseGateway<AccountOutput> baseGateway;
    private final GenericResponseMapper factory;

    public GetInformationAccountUseCase(ApplicationBaseGateway<AccountOutput> baseGateway, GenericResponseMapper factory) {
        this.baseGateway = baseGateway;
        this.factory = factory;
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        var cpf = (ICpfParam) param.input();
        var accountOutput = baseGateway.findById(cpf).get();
        var accountDto = AccountBuilder.buildFullAccountDto().createFrom(accountOutput);
        factory.fillIn(accountDto, param.output());
        return accountOutput;
    }
}
