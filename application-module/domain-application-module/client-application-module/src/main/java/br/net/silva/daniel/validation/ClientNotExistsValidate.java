package br.net.silva.daniel.validation;

import br.net.silva.daniel.exception.ExistsClientRegistredException;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Optional;

public class ClientNotExistsValidate implements IValidations, Validation<ClientOutput> {
    private final FindApplicationBaseGateway<ClientOutput> findClientRepository;

    public ClientNotExistsValidate () {
        this.findClientRepository = null;
    }

    public ClientNotExistsValidate(FindApplicationBaseGateway<ClientOutput> findClientRepository) {
        this.findClientRepository = findClientRepository;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var param = (ICpfParam) input.input();
        var optClient = findClientRepository.findById(param);

        if (optClient.isPresent()) {
            throw new ExistsClientRegistredException("Client already exists in database");
        }
    }

    @Override
    public void validate(Optional<ClientOutput> opt) throws GenericException {
        if (opt.isPresent()) {
            throw new ExistsClientRegistredException("Client already exists in database");
        }
    }
}
