package br.net.silva.business.usecase;

import br.net.silva.business.dto.FindAccountDTO;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;
import br.net.silva.daniel.shared.business.mapper.GenericMapper;

public class DeactivateAccountUseCase implements UseCase<IProcessResponse<AccountDTO>> {

    private final Repository<Account> deactivateAccountRepository;
    private final GenericMapper<FindAccountDTO> mapper;

    public DeactivateAccountUseCase(Repository<Account> deactivateAccountRepository) {
        this.deactivateAccountRepository = deactivateAccountRepository;
        this.mapper = new GenericMapper<>(FindAccountDTO.class);
    }

    @Override
    public Account exec(IGenericPort param) throws GenericException {
        try {
            var dto = mapper.map(param);
            return deactivateAccountRepository.exec(dto.cpf());
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
