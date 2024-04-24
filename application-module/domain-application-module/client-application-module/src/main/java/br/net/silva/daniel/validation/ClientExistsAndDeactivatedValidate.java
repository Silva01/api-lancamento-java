package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class ClientExistsAndDeactivatedValidate implements Validation<ClientOutput> {

    @Override
    public void validate(Optional<ClientOutput> optClient) throws GenericException {
        if (optClient.isEmpty()) {
            throw new ClientNotExistsException("Client not exists in database");
        }

        if (optClient.get().active()) {
            throw new ExistsClientRegistredException("Client already activated");
        }
    }
}
