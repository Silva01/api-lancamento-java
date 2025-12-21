package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ExistsClientRegistredExceptionDomain;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class ClientNotExistsValidate implements Validation<ClientOutput> {

    @Override
    public void validate(Optional<ClientOutput> opt) throws DomainGenericException {
        if (opt.isPresent()) {
            throw new ExistsClientRegistredExceptionDomain("Client already exists in database");
        }
    }
}
