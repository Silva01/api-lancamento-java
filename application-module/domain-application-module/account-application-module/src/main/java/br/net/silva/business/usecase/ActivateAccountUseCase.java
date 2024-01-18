package br.net.silva.business.usecase;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.mapper.MapToFindAccountMapper;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class ActivateAccountUseCase implements UseCase {

    private final Repository<Account> activateAccountRepository;
    private final Repository<Account> findAccountRepository;
    private final MapToFindAccountMapper mapper;

    public ActivateAccountUseCase(Repository<Account> activateAccountRepository, Repository<Account> findAccountRepository) {
        this.activateAccountRepository = activateAccountRepository;
        this.findAccountRepository = findAccountRepository;
        this.mapper = MapToFindAccountMapper.INSTANCE;
    }

    @Override
    public void exec(Source param) throws GenericException {
        try {
            var dto = mapper.mapToFindAccountDto(param.input());
            var account = findAccountRepository.exec(dto.account(), dto.agency(), dto.cpf());
            account.activate();
            var accountActive = activateAccountRepository.exec(account);
            param.map().put(TypeAccountMapperEnum.ACCOUNT.name(), accountActive.build());
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
