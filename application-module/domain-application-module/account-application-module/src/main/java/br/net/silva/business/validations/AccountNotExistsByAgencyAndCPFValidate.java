package br.net.silva.business.validations;

import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountExistsException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.business.mapper.MapToFindAccountMapper;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.value_object.Source;

import java.util.Optional;

public class AccountNotExistsByAgencyAndCPFValidate implements IValidations {

    private final Repository<Optional<Account>> findAccountRepository;
    private final MapToFindAccountMapper mapper;

    public AccountNotExistsByAgencyAndCPFValidate(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
        this.mapper = MapToFindAccountMapper.INSTANCE;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var dto = this.mapper.mapToFindAccountDto(input.input());
        var optionalAccount = findAccountRepository.exec(dto.agency(), dto.cpf());

        if (optionalAccount.isPresent()) {
            var accountDto = optionalAccount.get().build();
            if (accountDto.active()) {
                throw new AccountAlreadyActiveException("Account already active");
            }

            throw new AccountExistsException("Account Exists by agency and cpf");
        }



    }
}
