package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ClientDeactivatedExceptionDomain;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class ClientActivedValidation implements Validation<ClientOutput> {

    @Override
    public void validate(Optional<ClientOutput> opt) throws DomainGenericException {
        var isClientDeactivated = opt.isPresent() && !opt.get().active();
        if (isClientDeactivated)
            throw new ClientDeactivatedExceptionDomain("Client is Deactivated");
    }
}
