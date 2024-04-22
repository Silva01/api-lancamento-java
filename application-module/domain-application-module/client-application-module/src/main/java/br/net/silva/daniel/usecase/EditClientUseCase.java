package br.net.silva.daniel.usecase;

import br.net.silva.daniel.build.ClientBuilder;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.exceptions.ClientNotActiveException;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.validation.ClientExistsValidate;
import br.net.silva.daniel.value_object.input.EditClientInput;
import br.net.silva.daniel.value_object.output.ClientOutput;

@ValidateStrategyOn(validations = {ClientExistsValidate.class})
public final class EditClientUseCase implements UseCase<ClientOutput> {
    private final FindApplicationBaseGateway<ClientOutput> findRepository;
    private final SaveApplicationBaseGateway<ClientOutput> saveRepository;
    private final GenericResponseMapper mapper;

    public EditClientUseCase(ApplicationBaseGateway<ClientOutput> baseRepository, GenericResponseMapper mapper) {
        this.findRepository = baseRepository;
        this.saveRepository = baseRepository;
        this.mapper = mapper;
    }

    @Override
    public ClientOutput exec(Source param) throws GenericException {
        try {
            var input = (EditClientInput) param.input();

            var clientOutput = execValidate(findRepository.findById(input)).extract();

            final var client = updateClient(clientOutput, input);
            buildResponse(param, client.build());

            return saveRepository.save(convertToOutput(client));
        } catch (ClientNotActiveException cae) {
            throw new ClientNotActiveException(cae.getMessage());
        } catch (ClientNotExistsException cne) {
            throw cne;
        } catch (Exception e) {
            throw new GenericException("Generic error");
        }
    }

    private void buildResponse(Source param, ClientDTO dto) {
        mapper.fillIn(dto, param.output());
    }

    private static ClientOutput convertToOutput(Client client) {
        return ClientBuilder.buildFullClientOutput().createFrom(client.build());
    }

    private static Client updateClient(ClientOutput clientOutput, EditClientInput input) {
        var client = ClientBuilder.buildAggregate().createFrom(clientOutput);
        client.editName(input.name());
        client.editTelephone(input.telephone());
        return client;
    }
}
