package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class ClientExistsValidate implements IValidations, Validation<ClientOutput> {
    private final FindApplicationBaseGateway<ClientOutput> findClientRepository;

    public ClientExistsValidate(FindApplicationBaseGateway<ClientOutput> findClientRepository) {
        this.findClientRepository = findClientRepository;
    }

    public ClientExistsValidate() {
        this.findClientRepository = null;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var param = (ICpfParam) input.input();
        var optClient = findClientRepository.findById(param);

        if (optClient.isEmpty()) {
            throw new ClientNotExistsException("Client not exists in database");
        }
    }

    @Override
    public void validate(Optional<ClientOutput> opt) throws ClientNotExistsException {
        if (opt.isEmpty()) {
            throw new ClientNotExistsException("Client not exists in database");
        }
    }
}
