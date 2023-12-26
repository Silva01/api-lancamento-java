package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.CreateNewAccountByCpfDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.AccountExistsForCPFInformatedException;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateNewAccountByCpfFactory;
import br.net.silva.daniel.factory.IFactoryAggregate;
import br.net.silva.daniel.interfaces.ICreateAccountPord;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;

public class CreateNewAccountByCpfUseCase implements UseCase<CreateNewAccountByCpfDTO, AccountDTO> {

    private final IFactoryAggregate<Account, ICreateAccountPord> createNewAccountByCpfFactory;
    private final Repository<Boolean> findIsExistsPeerCPFRepository;
    private final Repository<Account> saveRepository;

    public CreateNewAccountByCpfUseCase(Repository<Boolean> findIsExistsPeerCPFRepository, Repository<Account> saveRepository) {
        this.findIsExistsPeerCPFRepository = findIsExistsPeerCPFRepository;
        this.saveRepository = saveRepository;
        this.createNewAccountByCpfFactory = new CreateNewAccountByCpfFactory();
    }

    @Override
    public AccountDTO exec(CreateNewAccountByCpfDTO dto) throws GenericException {
        if (isExistsAccountActiveForCPF(dto.cpf())) {
            throw new AccountExistsForCPFInformatedException("Exists account active for CPF informated");
        }
        var newAccount = saveRepository.exec(createNewAccountByCpfFactory.create(dto));
        return newAccount.build();
    }

    private boolean isExistsAccountActiveForCPF(String cpf) {
        return findIsExistsPeerCPFRepository.exec(cpf);
    }
}
