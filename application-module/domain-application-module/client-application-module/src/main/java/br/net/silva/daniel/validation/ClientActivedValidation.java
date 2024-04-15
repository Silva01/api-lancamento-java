package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ClientDeactivatedException;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class ClientActivedValidation implements Validation<ClientOutput> {

    @Override
    public void validate(Optional<ClientOutput> opt) throws GenericException {
        var isClientDeactivated = opt.isPresent() && !opt.get().active();
        if (isClientDeactivated)
            throw new ClientDeactivatedException("Client is Deactivated");
    }
}
