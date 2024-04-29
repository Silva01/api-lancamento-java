package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.value_object.Address;
import br.net.silva.daniel.value_object.input.EditAddressInput;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

@ValidateStrategyOn(validations = {ClientExistsValidate.class})
public final class EditAddressUseCase implements UseCase<ClientOutput> {

    private final FindApplicationBaseGateway<ClientOutput> findClientRepository;
    private final SaveApplicationBaseGateway<ClientOutput> saveClientRepository;

    public EditAddressUseCase(ApplicationBaseGateway<ClientOutput> baseRepository) {
        this.findClientRepository = baseRepository;
        this.saveClientRepository = baseRepository;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        final var editAddressInput = (EditAddressInput) param.input();
        final var clientOutput = execValidate(getClient(editAddressInput)).extract();
        final var client = registerNewAddress(clientOutput, editAddressInput);
        return saveClientRepository.save(ClientBuilder.buildFullClientOutput().createFrom(client.build()));
    }

    private static Client registerNewAddress(ClientOutput clientOutput, EditAddressInput editAddressInput) {
        final var client = ClientBuilder.buildAggregate().createFrom(clientOutput);
        client.registerAddress(buildAddress(editAddressInput));
        return client;
    }

    private Optional<ClientOutput> getClient(EditAddressInput editAddressInput) {
        return findClientRepository.findById(editAddressInput);
    }

    private static Address buildAddress(EditAddressInput editAddressInput) {
        return new Address(
                editAddressInput.street(),
                editAddressInput.number(),
                editAddressInput.complement(),
                editAddressInput.neighborhood(),
                editAddressInput.state(),
                editAddressInput.city(),
                editAddressInput.zipCode()
        );
    }
}
