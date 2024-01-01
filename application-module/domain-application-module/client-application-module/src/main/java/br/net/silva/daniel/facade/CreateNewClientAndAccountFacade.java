package br.net.silva.daniel.facade;

import br.net.silva.business.facade.CreateNewAccountByCpfFacade;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.dto.ClientRequestDTO;
import br.net.silva.business.dto.CreateNewAccountByCpfDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IFacade;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;

public class CreateNewClientAndAccountFacade implements IFacade<ClientRequestDTO, AccountDTO> {

    private final IFacade<CreateNewAccountByCpfDTO, AccountDTO> createNewAccountByCpfFacade;
    private final UseCase<ClientRequestDTO, ClientDTO> createNewClientUseCase;

    public CreateNewClientAndAccountFacade(final Repository<Boolean> findIsExistsPeerCpfRepository, final Repository<Account> accountSaveRepository, Repository<Client> clientSaveRepository) {
        this.createNewAccountByCpfFacade = new CreateNewAccountByCpfFacade(findIsExistsPeerCpfRepository, accountSaveRepository);
        this.createNewClientUseCase = new CreateNewClientUseCase(clientSaveRepository);
    }
    @Override
    public AccountDTO execute(ClientRequestDTO dto) throws GenericException {
        var clientDTO = createNewClientUseCase.exec(dto);
        return createNewAccountByCpfFacade.execute(new CreateNewAccountByCpfDTO(clientDTO.cpf(), dto.agencyNumber()));
    }
}
