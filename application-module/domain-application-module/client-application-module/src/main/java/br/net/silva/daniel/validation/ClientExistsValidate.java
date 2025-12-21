package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ClientNotExistsExceptionDomain;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class ClientExistsValidate implements Validation<ClientOutput> {

    @Override
    public void validate(Optional<ClientOutput> opt) throws ClientNotExistsExceptionDomain {
        if (opt.isEmpty()) {
            throw new ClientNotExistsExceptionDomain("Client not exists in database");
        }
    }
}
