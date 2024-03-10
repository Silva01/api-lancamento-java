package br.net.silva.business.build;

import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.ApplicationBaseRepository;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public class CreateNewAccountByCpfUseCaseBuilder implements ObjectBuilder<UseCase<AccountOutput>> {

    private ApplicationBaseRepository<AccountOutput> baseRepository;
    private GenericResponseMapper factory;

    public CreateNewAccountByCpfUseCaseBuilder withRepositoryBase(ApplicationBaseRepository<AccountOutput> findIsExistsPeerCPFRepository) {
        this.baseRepository = findIsExistsPeerCPFRepository;
        return this;
    }

    public CreateNewAccountByCpfUseCaseBuilder withFactory(GenericResponseMapper factory) {
        this.factory = factory;
        return this;
    }
    @Override
    public UseCase<AccountOutput> build() {
        ValidateUtils.requireNotNull(baseRepository, "Repository for save is required");
        ValidateUtils.requireNotNull(factory, "Factory is required");
        return new CreateNewAccountByCpfUseCase(baseRepository, factory);
    }
}
