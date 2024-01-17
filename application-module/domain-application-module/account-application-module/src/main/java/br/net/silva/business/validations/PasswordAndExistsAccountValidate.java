package br.net.silva.business.validations;

import br.net.silva.business.enums.TypeAccountMapperEnum;
import br.net.silva.business.mapper.MapToFindAccountMapper;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.factory.CreateAccountByAccountDTOFactory;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import br.net.silva.daniel.utils.AccountUtils;
import br.net.silva.daniel.shared.business.value_object.Source;

public class PasswordAndExistsAccountValidate implements IValidations {

    private final UseCase findAccountUseCase;
    private final MapToFindAccountMapper mapper;
    private final CreateAccountByAccountDTOFactory factory;

    public PasswordAndExistsAccountValidate(UseCase findAccountUseCase) {
        this.findAccountUseCase = findAccountUseCase;
        this.mapper = MapToFindAccountMapper.INSTANCE;
        this.factory = new CreateAccountByAccountDTOFactory();
    }

    @Override
    public void validate(Source input) throws GenericException {
        var findAccountParamDTO = this.mapper.mapToFindAccountDto(input.input());
        AccountUtils.validatePassword(findAccountParamDTO.password());
        findAccountUseCase.exec(input);

        var account = factory.create((AccountDTO) input.map().get(TypeAccountMapperEnum.ACCOUNT.name()));
        account.validatePassword(CryptoUtils.convertToSHA256(findAccountParamDTO.password()));
    }
}
