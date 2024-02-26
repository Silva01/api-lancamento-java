package br.net.silva.business.usecase;

import br.net.silva.business.build.AccountBuilder;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class DeactivateAccountUseCase implements UseCase<AccountOutput> {

    private final Repository<Account> deactivateAccountRepository;
    public DeactivateAccountUseCase(Repository<Account> deactivateAccountRepository) {
        this.deactivateAccountRepository = deactivateAccountRepository;
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        try {
            var dto = (ICpfParam) param.input();
            var accountAggregate =  deactivateAccountRepository.exec(dto.cpf());
            return AccountBuilder.buildFullAccountOutput().createFrom(accountAggregate.build());
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
