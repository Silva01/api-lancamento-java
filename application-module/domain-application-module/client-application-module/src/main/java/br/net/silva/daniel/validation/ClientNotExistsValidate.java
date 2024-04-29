package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class ClientNotExistsValidate implements Validation<ClientOutput> {

    @Override
    public void validate(Optional<ClientOutput> opt) throws GenericException {
        if (opt.isPresent()) {
            throw new ExistsClientRegistredException("Client already exists in database");
        }
    }
}
