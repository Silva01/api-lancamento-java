package br.net.silva.business.usecase;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.exception.AccountExistsForCPFInformatedException;
import br.net.silva.business.mapper.MapToCreateNewAccountByCpfMapper;
import br.net.silva.daniel.utils.ConverterUtils;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateNewAccountByCpfFactory;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.value_object.Source;

public class CreateNewAccountByCpfUseCase implements UseCase {

    private final IFactoryAggregate<Account, IGenericPort> createNewAccountByCpfFactory;
    private final Repository<Boolean> findIsExistsPeerCPFRepository;
    private final Repository<Account> saveRepository;
    private final MapToCreateNewAccountByCpfMapper mapper;

    public CreateNewAccountByCpfUseCase(Repository<Boolean> findIsExistsPeerCPFRepository, Repository<Account> saveRepository) {
        this.findIsExistsPeerCPFRepository = findIsExistsPeerCPFRepository;
        this.saveRepository = saveRepository;
        this.createNewAccountByCpfFactory = new CreateNewAccountByCpfFactory();
        this.mapper = MapToCreateNewAccountByCpfMapper.INSTANCE;
    }

    @Override
    public void exec(Source param) throws GenericException {
        var createNewAccountByCpfDTO = mapper.mapToCreateNewAccountByCpfDto(param.input());
        if (isExistsAccountActiveForCPF(createNewAccountByCpfDTO.cpf())) {
            throw new AccountExistsForCPFInformatedException("Exists account active for CPF informated");
        }
        var accountAggregate = saveRepository.exec(createNewAccountByCpfFactory.create(createNewAccountByCpfDTO));
        param.map().put(TypeAccountMapperEnum.ACCOUNT.name(), accountAggregate.build());
    }

    private boolean isExistsAccountActiveForCPF(String cpf) {
        return findIsExistsPeerCPFRepository.exec(cpf);
    }
}
