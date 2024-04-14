package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.input.ActivateAccount;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.shared.application.interfaces.EmptyOutput;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;

public final class ActivateAccountUseCase implements UseCase<EmptyOutput> {

    private final Repository<AccountOutput> activateAccountRepository;
    private final Repository<AccountOutput> findAccountRepository;

    private final CreateAccountByAccountDTOFactory accountFactory;

    public ActivateAccountUseCase(Repository<AccountOutput> activateAccountRepository, Repository<AccountOutput> findAccountRepository) {
        this.activateAccountRepository = activateAccountRepository;
        this.findAccountRepository = findAccountRepository;
        this.accountFactory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public EmptyOutput exec(Source param) throws GenericException {
        try {
            var dto = (ActivateAccount) param.input();
            var accountOutput = findAccountRepository.exec(dto.accountNumber(), dto.agency(), dto.cpf());
            var account = accountFactory.create(AccountBuilder.buildFullAccountDto().createFrom(accountOutput));
            account.activate();
            activateAccountRepository.exec(AccountBuilder.buildFullAccountOutput().createFrom(account.build()));
            return EmptyOutput.INSTANCE;
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
