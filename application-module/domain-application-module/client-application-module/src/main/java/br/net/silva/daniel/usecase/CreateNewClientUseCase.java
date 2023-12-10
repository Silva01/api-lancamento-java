package br.net.silva.daniel.usecase;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.factory.CreateNewAddressFactory;
import br.net.silva.daniel.factory.CreateNewClientFactory;
import br.net.silva.daniel.repository.SaveRepository;
import br.net.silva.daniel.template.UseCase;

public class CreateNewClientUseCase extends UseCase<ClientDTO> {
    private final SaveRepository<Client> saveRepository;
    private final CreateNewClientFactory createNewClientFactory;

    public CreateNewClientUseCase(SaveRepository<Client> saveRepository) {
        this.saveRepository = saveRepository;
        this.createNewClientFactory = new CreateNewClientFactory(new CreateNewAddressFactory());
    }

    @Override
    public void exec(ClientDTO dto) throws GenericException {
        try {
            var aggregate = createNewClientFactory.create(dto);
            saveRepository.save(aggregate);
        } catch (Exception e) {
            throw new ExistsClientRegistredException(e.getMessage());
        }
    }
}
