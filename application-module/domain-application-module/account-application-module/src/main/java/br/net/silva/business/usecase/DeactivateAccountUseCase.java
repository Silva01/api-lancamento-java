package br.net.silva.business.usecase;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.mapper.MapToFindAccountMapper;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.value_object.Source;

public class DeactivateAccountUseCase implements UseCase {

    private final Repository<Account> deactivateAccountRepository;
    private final MapToFindAccountMapper mapper;
    public DeactivateAccountUseCase(Repository<Account> deactivateAccountRepository) {
        this.deactivateAccountRepository = deactivateAccountRepository;
        this.mapper = MapToFindAccountMapper.INSTANCE;
    }

    @Override
    public void exec(Source param) throws GenericException {
        try {
            var dto = mapper.mapToFindAccountDto(param.input());
            var accountAggregate =  deactivateAccountRepository.exec(dto.cpf());
            param.map().put(TypeAccountMapperEnum.ACCOUNT.name(), accountAggregate.build());
        } catch (Exception e) {
            throw new GenericException(e.getMessage());
        }
    }
}
