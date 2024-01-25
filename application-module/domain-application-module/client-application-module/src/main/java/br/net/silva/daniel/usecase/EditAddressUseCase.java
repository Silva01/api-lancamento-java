package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.value_object.Source;
import br.net.silva.daniel.value_object.input.EditAddressInput;

public class EditAddressUseCase implements UseCase<ClientDTO> {

    private final Repository<Client> findClientRepository;
    private final Repository<Client> saveClientRepository;

    public EditAddressUseCase(Repository<Client> findClientRepository, Repository<Client> saveClientRepository) {
        this.findClientRepository = findClientRepository;
        this.saveClientRepository = saveClientRepository;
    }

    @Override
    public ClientDTO exec(Source param) throws GenericException {
        try {
            var editAddressInput = (EditAddressInput) param.input();
            var address = new Address(
                    editAddressInput.street(),
                    editAddressInput.number(),
                    editAddressInput.complement(),
                    editAddressInput.neighborhood(),
                    editAddressInput.state(),
                    editAddressInput.city(),
                    editAddressInput.zipCode()
            );
            var client = findClientRepository.exec(editAddressInput.cpf());
            client.registerAddress(address);
            return saveClientRepository.exec(client).build();
        } catch (Exception e) {
            throw new GenericException("Generic Error", e);
        }
    }
}
