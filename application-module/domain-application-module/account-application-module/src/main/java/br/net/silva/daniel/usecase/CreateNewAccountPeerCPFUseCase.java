package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.CreateNewAccountForCpfDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.AccountExistsForCPFInformatedException;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateNewAccountByCpfFactory;
import br.net.silva.daniel.factory.IFactoryAggregate;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;

public class CreateNewAccountPeerCPFUseCase implements UseCase<CreateNewAccountForCpfDTO, AccountDTO> {

    private final IFactoryAggregate<Account, AccountDTO> createNewAccountByCpfFactory;
    private final Repository<Boolean> findIsExistsPeerCPFRepository;
    private final Repository<Account> saveRepository;

    public CreateNewAccountPeerCPFUseCase(Repository<Boolean> findIsExistsPeerCPFRepository, Repository<Account> saveRepository) {
        this.findIsExistsPeerCPFRepository = findIsExistsPeerCPFRepository;
        this.saveRepository = saveRepository;
        this.createNewAccountByCpfFactory = new CreateNewAccountByCpfFactory();
    }

    @Override
    public AccountDTO exec(CreateNewAccountForCpfDTO dto) throws GenericException {
        if (isExistsAccountActiveForCPF(dto.cpf())) {
            throw new AccountExistsForCPFInformatedException("Exists account active for CPF informated");
        }

        //TODO: Refatorar a instrução abaixo
        var accountDTO = new AccountDTO(null, dto.agency(), null, "123456",  true, dto.cpf(), null, null);
        var newAccount = saveRepository.exec(createNewAccountByCpfFactory.create(accountDTO));
        return newAccount.create();
    }

    private boolean isExistsAccountActiveForCPF(String cpf) {
        return findIsExistsPeerCPFRepository.exec(cpf);
    }
}
