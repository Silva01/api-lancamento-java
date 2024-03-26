package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.business.utils.GenericErrorUtils;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.input.EditAddressInput;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class EditAddressUseCase implements UseCase<ClientOutput> {

    private final FindApplicationBaseGateway<ClientOutput> findClientRepository;
    private final SaveApplicationBaseGateway<ClientOutput> saveClientRepository;

    public EditAddressUseCase(ApplicationBaseGateway<ClientOutput> baseRepository) {
        this.findClientRepository = baseRepository;
        this.saveClientRepository = baseRepository;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
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
            var clientOutput = findClientRepository.findById(editAddressInput)
                    .orElseThrow(() -> GenericErrorUtils.executeException("Client not found"));

            var client = ClientBuilder.buildAggregate().createFrom(clientOutput);
            client.registerAddress(address);
            return saveClientRepository.save(ClientBuilder.buildFullClientOutput().createFrom(client.build()));
        } catch (Exception e) {
            throw new GenericException("Generic Error", e);
        }
    }
}
