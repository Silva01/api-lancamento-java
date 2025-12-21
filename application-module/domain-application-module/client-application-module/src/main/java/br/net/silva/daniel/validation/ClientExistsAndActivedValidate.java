package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ClientDeactivatedExceptionDomain;
import br.net.silva.daniel.exception.ClientNotExistsExceptionDomain;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class ClientExistsAndActivedValidate implements Validation<ClientOutput> {

    @Override
    public void validate(Optional<ClientOutput> optClient) throws DomainGenericException {
        if (optClient.isEmpty()) {
            throw new ClientNotExistsExceptionDomain("Client not exists in database");
        }

        if (!optClient.get().active()) {
            throw new ClientDeactivatedExceptionDomain("Client is Deactivated");
        }
    }
}
