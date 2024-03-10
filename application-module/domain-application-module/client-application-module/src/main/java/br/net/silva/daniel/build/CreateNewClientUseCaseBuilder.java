package br.net.silva.daniel.build;

import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.SaveApplicationBaseRepository;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;
import br.net.silva.daniel.usecase.CreateNewClientUseCase;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class CreateNewClientUseCaseBuilder implements ObjectBuilder<UseCase<ClientOutput>> {

    private SaveApplicationBaseRepository<ClientOutput> saveRepository;
    private GenericResponseMapper factory;

    public CreateNewClientUseCaseBuilder withRepository(SaveApplicationBaseRepository<ClientOutput> saveRepository) {
        this.saveRepository = saveRepository;
        return this;
    }

    public CreateNewClientUseCaseBuilder withFactory(GenericResponseMapper factory) {
        this.factory = factory;
        return this;
    }

    @Override
    public UseCase<ClientOutput> build() {
        ValidateUtils.requireNotNull(saveRepository, "Repository is required");
        ValidateUtils.requireNotNull(factory, "Factory is required");
        return new CreateNewClientUseCase(saveRepository, factory);
    }
}
