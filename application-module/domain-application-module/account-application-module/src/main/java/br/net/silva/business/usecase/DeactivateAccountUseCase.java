package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;

public class DeactivateAccountUseCase implements UseCase<AccountOutput> {

    private final ApplicationBaseGateway<AccountOutput> baseGateway;
    private final FindAccountByCpfUseCase findAccountUseCase;

    public DeactivateAccountUseCase(ApplicationBaseGateway<AccountOutput> baseGateway, GenericResponseMapper mapper) {
        this.baseGateway = baseGateway;
        this.findAccountUseCase = new FindAccountByCpfUseCase(baseGateway, mapper);
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        try {
            var account = AccountBuilder.buildAggregate().createFrom(findAccountUseCase.exec(param));
            account.deactivate();
            return baseGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
