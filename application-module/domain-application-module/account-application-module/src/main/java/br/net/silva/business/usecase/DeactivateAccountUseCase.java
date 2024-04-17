package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

public class DeactivateAccountUseCase implements UseCase<AccountOutput> {

    private final ApplicationBaseGateway<AccountOutput> baseAccountGateway;

    public DeactivateAccountUseCase(ApplicationBaseGateway<AccountOutput> baseAccountGateway) {
        this.baseAccountGateway = baseAccountGateway;
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        try {
            var input = (ICpfParam) param.input();
            var optAccount = baseAccountGateway.findById(input);

            if (optAccount.isEmpty()) {
                throw new AccountNotExistsException("Account not found");
            }

            var account = AccountBuilder.buildAggregate().createFrom(optAccount.get());
            account.deactivate();
            return baseAccountGateway.save(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));
        } catch (Exception e) {
            throw new AccountNotExistsException(e.getMessage());
        }
    }
}
