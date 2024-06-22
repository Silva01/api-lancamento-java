package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ClientNotExistsException;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class ClientExistsValidate implements Validation<ClientOutput> {

    @Override
    public void validate(Optional<ClientOutput> opt) throws ClientNotExistsException {
        if (opt.isEmpty()) {
            throw new ClientNotExistsException("Client not exists in database");
        }
    }
}
