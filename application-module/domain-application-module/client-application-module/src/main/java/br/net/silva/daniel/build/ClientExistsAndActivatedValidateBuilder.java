package br.net.silva.daniel.build;

import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.validation.ClientExistsAndActivedValidate;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Objects;

public class ClientExistsAndActivatedValidateBuilder implements ObjectBuilder<IValidations> {

    private FindApplicationBaseGateway<ClientOutput> findClientRepository;

    public ClientExistsAndActivatedValidateBuilder withRepository(FindApplicationBaseGateway<ClientOutput> findClientRepository) {
        this.findClientRepository = findClientRepository;
        return this;
    }

    @Override
    public IValidations build() {
        Objects.requireNonNull(findClientRepository, "UseCase is required");
        return new ClientExistsAndActivedValidate(findClientRepository);
    }
}
