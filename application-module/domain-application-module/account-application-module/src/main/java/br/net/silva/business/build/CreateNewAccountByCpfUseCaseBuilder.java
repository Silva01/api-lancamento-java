package br.net.silva.business.build;

import br.net.silva.business.usecase.CreateNewAccountByCpfUseCase;
import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.build.ObjectBuilder;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import br.net.silva.daniel.shared.application.repository.Repository;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public class CreateNewAccountByCpfUseCaseBuilder implements ObjectBuilder<UseCase<AccountOutput>> {

    private Repository<Boolean> findIsExistsPeerCPFRepository;
    private Repository<AccountOutput> saveRepository;
    private GenericResponseMapper factory;

    public CreateNewAccountByCpfUseCaseBuilder withRepositoryForFind(Repository<Boolean> findIsExistsPeerCPFRepository) {
        this.findIsExistsPeerCPFRepository = findIsExistsPeerCPFRepository;
        return this;
    }

    public CreateNewAccountByCpfUseCaseBuilder withRepositoryForSave(Repository<AccountOutput> saveRepository) {
        this.saveRepository = saveRepository;
        return this;
    }

    public CreateNewAccountByCpfUseCaseBuilder withFactory(GenericResponseMapper factory) {
        this.factory = factory;
        return this;
    }
    @Override
    public UseCase<AccountOutput> build() {
        ValidateUtils.requireNotNull(findIsExistsPeerCPFRepository, "Repository for find is required");
        ValidateUtils.requireNotNull(saveRepository, "Repository for save is required");
        ValidateUtils.requireNotNull(factory, "Factory is required");
        return new CreateNewAccountByCpfUseCase(findIsExistsPeerCPFRepository, saveRepository, factory);
    }
}
