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

public class ActivateAccountUseCase implements UseCase<IProcessResponse<AccountDTO>> {

    private final Repository<Account> activateAccountRepository;
    private final Repository<Account> findAccountRepository;
    private final GenericMapper<FindAccountDTO> mapper;

    public ActivateAccountUseCase(Repository<Account> activateAccountRepository, Repository<Account> findAccountRepository) {
        this.activateAccountRepository = activateAccountRepository;
        this.findAccountRepository = findAccountRepository;
        this.mapper = new GenericMapper<>(FindAccountDTO.class);
    }

    @Override
    public IProcessResponse<AccountDTO> exec(IGenericPort param) throws GenericException {
        try {
            var dto = mapper.map(param);
            var account = findAccountRepository.exec(dto.account(), dto.agency(), dto.cpf());
            account.activate();
            return activateAccountRepository.exec(account);
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
