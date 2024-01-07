package br.net.silva.business.usecase;

import br.net.silva.business.dto.CreateNewAccountByCpfDTO;
import br.net.silva.business.exception.AccountExistsForCPFInformatedException;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateNewAccountByCpfFactory;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.shared.business.mapper.GenericMapper;
import br.net.silva.daniel.repository.Repository;

public class CreateNewAccountByCpfUseCase implements UseCase<IProcessResponse<AccountDTO>> {

    private final IFactoryAggregate<Account, IGenericPort> createNewAccountByCpfFactory;
    private final Repository<Boolean> findIsExistsPeerCPFRepository;
    private final Repository<Account> saveRepository;
    private final GenericMapper<CreateNewAccountByCpfDTO> mapper;

    public CreateNewAccountByCpfUseCase(Repository<Boolean> findIsExistsPeerCPFRepository, Repository<Account> saveRepository) {
        this.findIsExistsPeerCPFRepository = findIsExistsPeerCPFRepository;
        this.saveRepository = saveRepository;
        this.createNewAccountByCpfFactory = new CreateNewAccountByCpfFactory();
        this.mapper = new GenericMapper<>(CreateNewAccountByCpfDTO.class);
    }

    @Override
    public IProcessResponse<AccountDTO> exec(IGenericPort dto) throws GenericException {
        var createNewAccountByCpfDTO = mapper.map(dto);
        if (isExistsAccountActiveForCPF(createNewAccountByCpfDTO.cpf())) {
            throw new AccountExistsForCPFInformatedException("Exists account active for CPF informated");
        }
        return saveRepository.exec(createNewAccountByCpfFactory.create(createNewAccountByCpfDTO));
    }

    private boolean isExistsAccountActiveForCPF(String cpf) {
        return findIsExistsPeerCPFRepository.exec(cpf);
    }
}
