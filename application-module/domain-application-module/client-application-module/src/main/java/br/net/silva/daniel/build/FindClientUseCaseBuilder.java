package br.net.silva.daniel.build;

import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseRepository;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;
import br.net.silva.daniel.usecase.FindClientUseCase;
import br.net.silva.daniel.value_object.output.ClientOutput;

public class FindClientUseCaseBuilder implements ObjectBuilder<UseCase<ClientOutput>> {

    private FindApplicationBaseRepository<ClientOutput> findClientRepository;
    private GenericResponseMapper factory;

    public static FindClientUseCaseBuilder create() {
        return new FindClientUseCaseBuilder();
    }

    public FindClientUseCaseBuilder withRepository(FindApplicationBaseRepository<ClientOutput> findClientRepository) {
        this.findClientRepository = findClientRepository;
        return this;
    }

    public FindClientUseCaseBuilder withFactory(GenericResponseMapper factory) {
        this.factory = factory;
        return this;
    }

    @Override
    public UseCase<ClientOutput> build() {
        ValidateUtils.requireNotNull(findClientRepository, "Repository is required");
        ValidateUtils.requireNotNull(factory, "Factory is required");
        return new FindClientUseCase(findClientRepository, factory);
    }
}
