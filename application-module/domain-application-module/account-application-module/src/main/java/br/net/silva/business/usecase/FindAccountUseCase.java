package br.net.silva.business.usecase;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.mapper.MapToFindAccountMapper;
import br.net.silva.daniel.utils.ConverterUtils;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class FindAccountUseCase implements UseCase {

    private final Repository<Optional<Account>> findAccountRepository;
    private final MapToFindAccountMapper mapper;

    public FindAccountUseCase(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
        this.mapper = MapToFindAccountMapper.INSTANCE;
    }

    @Override
    public void exec(Source param) throws GenericException {
        var findAccountDto = mapper.mapToFindAccountDto(param.input());
        var accountOptional = findAccountRepository.exec(findAccountDto.account(), findAccountDto.agency());
        var accountAggregate =  accountOptional.orElseThrow(() -> new AccountNotExistsException("Account not found"));
        param.map().put(TypeAccountMapperEnum.ACCOUNT.name(), ConverterUtils.convertJsonToMap(ConverterUtils.convertObjectToJson(accountAggregate.build())));
    }
}
