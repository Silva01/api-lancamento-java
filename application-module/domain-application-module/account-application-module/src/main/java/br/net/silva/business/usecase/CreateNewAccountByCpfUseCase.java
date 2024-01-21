package br.net.silva.business.usecase;

import br.net.silva.business.exception.AccountExistsForCPFInformatedException;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateNewAccountByCpfFactory;
import br.net.silva.daniel.mapper.GenericResponseMapper;
import br.net.silva.daniel.interfaces.IAgencyParam;
import br.net.silva.daniel.interfaces.ICpfParam;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.value_object.Source;

public class CreateNewAccountByCpfUseCase implements UseCase<AccountDTO> {

    private final IFactoryAggregate<Account, AccountDTO> createNewAccountByCpfFactory;
    private final Repository<Boolean> findIsExistsPeerCPFRepository;
    private final Repository<Account> saveRepository;
    private final GenericResponseMapper factory;

    public CreateNewAccountByCpfUseCase(Repository<Boolean> findIsExistsPeerCPFRepository, Repository<Account> saveRepository, GenericResponseMapper factory) {
        this.findIsExistsPeerCPFRepository = findIsExistsPeerCPFRepository;
        this.saveRepository = saveRepository;
        this.factory = factory;
        this.createNewAccountByCpfFactory = new CreateNewAccountByCpfFactory();
    }

    @Override
    public AccountDTO exec(Source param) throws GenericException {
        var clientCpf = (ICpfParam) param.input();
        var agencyInterface = (IAgencyParam) param.input();
        if (isExistsAccountActiveForCPF(clientCpf.cpf())) {
            throw new AccountExistsForCPFInformatedException("Exists account active for CPF informated");
        }
        var accountAggregate = saveRepository.exec(createNewAccountByCpfFactory.create(
                new AccountDTO(
                        null,
                        agencyInterface.agency(),
                        null,
                        "default",
                        true,
                        clientCpf.cpf(),
                        null,
                        null)));

        factory.fillIn(accountAggregate.build(), param.output());
        return accountAggregate.build();
    }

    private boolean isExistsAccountActiveForCPF(String cpf) {
        return findIsExistsPeerCPFRepository.exec(cpf);
    }
}
