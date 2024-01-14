package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.mapper.MapToFindAccountMapper;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class AccountExistsValidate implements IValidations {

    private final Repository<Optional<Account>> findAccountRepository;
    private final MapToFindAccountMapper mapper;

    public AccountExistsValidate(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
        this.mapper = MapToFindAccountMapper.INSTANCE;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var dto = this.mapper.mapToFindAccountDto(input.input());
        var optionalAccount = findAccountRepository.exec(dto.account(), dto.agency(), dto.cpf());

        if (optionalAccount.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        var accountDto = optionalAccount.get().build();
        if (accountDto.active()) {
            throw new AccountAlreadyActiveException("Account already active");
        }

    }
}
