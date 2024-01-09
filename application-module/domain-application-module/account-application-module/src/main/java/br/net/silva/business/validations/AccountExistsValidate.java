package br.net.silva.business.validations;

import br.net.silva.business.dto.FindAccountDTO;
import br.net.silva.business.exception.AccountAlreadyActiveException;
import br.net.silva.business.exception.AccountNotExistsException;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.mapper.GenericMapper;

import java.util.Optional;

public class AccountExistsValidate implements IValidations {

    private final Repository<Optional<Account>> findAccountRepository;
    private final GenericMapper<FindAccountDTO> mapper;

    public AccountExistsValidate(Repository<Optional<Account>> findAccountRepository) {
        this.findAccountRepository = findAccountRepository;
        this.mapper = new GenericMapper<>(FindAccountDTO.class);
    }

    @Override
    public void validate(IGenericPort param) throws GenericException {
        var dto = this.mapper.map(param);
        var optionalAccount = findAccountRepository.exec(dto.accountNumber(), dto.agency(), dto.cpf());

        if (optionalAccount.isEmpty()) {
            throw new AccountNotExistsException("Account not exists");
        }

        var accountDto = optionalAccount.get().build();
        if (accountDto.active()) {
            throw new AccountAlreadyActiveException("Account already active");
        }

    }
}
