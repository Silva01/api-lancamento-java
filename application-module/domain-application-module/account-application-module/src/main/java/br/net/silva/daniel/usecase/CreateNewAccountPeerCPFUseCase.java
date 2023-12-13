package br.net.silva.daniel.usecase;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateNewAccountFactory;
import br.net.silva.daniel.factory.IFactoryAggregate;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;

public class CreateNewAccountPeerCPFUseCase implements UseCase<String, AccountDTO> {

    private final IFactoryAggregate<Account, AccountDTO> createNewAccountFactory;
    private final Repository<Boolean> findIsExistsPeerCPFRepository;
    private final Repository<Account> saveRepository;

    public CreateNewAccountPeerCPFUseCase(Repository<Boolean> findIsExistsPeerCPFRepository, Repository<Account> saveRepository) {
        this.findIsExistsPeerCPFRepository = findIsExistsPeerCPFRepository;
        this.saveRepository = saveRepository;
        this.createNewAccountFactory = new CreateNewAccountFactory();
    }

    @Override
    public AccountDTO exec(String cpf) throws GenericException {
        if (isExistsAccountActiveForCPF(cpf)) {

        }



        return null;
    }

    private boolean isExistsAccountActiveForCPF(String cpf) {
        return findIsExistsPeerCPFRepository.exec(cpf);
    }
}
