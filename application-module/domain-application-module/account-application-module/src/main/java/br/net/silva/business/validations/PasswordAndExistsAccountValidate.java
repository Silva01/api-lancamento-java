package br.net.silva.business.validations;

import br.net.silva.business.mapper.MapToAccountMapper;
import br.net.silva.business.mapper.MapToFindAccountMapper;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.AccountUtils;
import br.net.silva.daniel.value_object.Source;

public class PasswordAndExistsAccountValidate implements IValidations {

    private final UseCase findAccountUseCase;
    private final MapToFindAccountMapper mapper;
    private final MapToAccountMapper accountMapper;
    private final CreateAccountByAccountDTOFactory factory;

    public PasswordAndExistsAccountValidate(UseCase findAccountUseCase) {
        this.findAccountUseCase = findAccountUseCase;
        this.mapper = MapToFindAccountMapper.INSTANCE;
        this.factory = new CreateAccountByAccountDTOFactory();
        this.accountMapper = MapToAccountMapper.INSTANCE;
    }

    @Override
    public void validate(Source input) throws GenericException {
        var findAccountParamDTO = this.mapper.mapToFindAccountDto(input.input());
        AccountUtils.validatePassword(findAccountParamDTO.password());
        findAccountUseCase.exec(input);

        var account = factory.create(accountMapper.mapToAccountDTO(input));
        account.validatePassword(CryptoUtils.convertToSHA256(findAccountParamDTO.password()));
    }
}
