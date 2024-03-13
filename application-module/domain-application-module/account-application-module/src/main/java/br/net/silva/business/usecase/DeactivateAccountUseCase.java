package br.net.silva.business.usecase;

import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.Repository;
import br.net.silva.daniel.shared.application.value_object.Source;

public class DeactivateAccountUseCase implements UseCase<AccountOutput> {

    private final Repository<AccountOutput> deactivateAccountRepository;
    public DeactivateAccountUseCase(Repository<AccountOutput> deactivateAccountRepository) {
        this.deactivateAccountRepository = deactivateAccountRepository;
    }

    @Override
    public AccountOutput exec(Source param) throws GenericException {
        try {
            var dto = (ICpfParam) param.input();
            return deactivateAccountRepository.exec(dto.cpf());
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
