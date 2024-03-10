package br.net.silva.daniel.build;

import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.interfaces.IValidations;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.validation.ClientNotExistsValidate;
import br.net.silva.daniel.value_object.output.ClientOutput;

import java.util.Objects;

public class ClientNotExistsValidateBuilder implements ObjectBuilder<IValidations> {

    private UseCase<ClientOutput> findClientUseCase;

    public ClientNotExistsValidateBuilder withUseCase(UseCase<ClientOutput> findClientUseCase) {
        this.findClientUseCase = findClientUseCase;
        return this;
    }

    @Override
    public IValidations build() {
        Objects.requireNonNull(findClientUseCase, "UseCase is required");
        return new ClientNotExistsValidate(findClientUseCase);
    }
}
