package br.net.silva.daniel.facade;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.CreateNewAccountByCpfDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IFacade;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.CreateNewAccountPeerCPFUseCase;

public class CreateNewAccountByCpfFacade implements IFacade<CreateNewAccountByCpfDTO, AccountDTO> {
    private final UseCase<CreateNewAccountByCpfDTO, AccountDTO> createNewAccountPeerCPFUseCase;

    public CreateNewAccountByCpfFacade(Repository<Boolean> findIsExistsPeerCPFRepository, Repository<Account> saveRepository) {
        this.createNewAccountPeerCPFUseCase = new CreateNewAccountPeerCPFUseCase(findIsExistsPeerCPFRepository, saveRepository);
    }

    @Override
    public AccountDTO execute(CreateNewAccountByCpfDTO dto) throws GenericException {
        return createNewAccountPeerCPFUseCase.exec(dto);
    }
}
