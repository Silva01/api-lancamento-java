package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class ClientExistsAndDeactivatedValidate implements IValidations {
    private final FindApplicationBaseGateway<ClientOutput> findClientRepository;

    public ClientExistsAndDeactivatedValidate(FindApplicationBaseGateway<ClientOutput> findClientRepository) {
        this.findClientRepository = findClientRepository;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var param = (ICpfParam) input.input();
        var optClient = findClientRepository.findById(param);

        if (optClient.isEmpty()) {
            throw new ClientNotExistsException("Client not exists in database");
        }

        if (optClient.get().active()) {
            throw new ExistsClientRegistredException("Client already activated");
        }
    }
}
