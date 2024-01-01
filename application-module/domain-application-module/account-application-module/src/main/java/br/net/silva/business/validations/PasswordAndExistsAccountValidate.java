package br.net.silva.business.validations;

import br.net.silva.business.utils.CryptoUtils;
import br.net.silva.business.dto.FindAccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IGenericPort;
import br.net.silva.daniel.interfaces.IProcessResponse;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericMapper;

public class PasswordAndExistsAccountValidate implements IValidations {

    private final UseCase<IProcessResponse<? extends IGenericPort>> findAccountUseCase;
    private final GenericMapper<FindAccountDTO> mapper;

    public PasswordAndExistsAccountValidate(UseCase<IProcessResponse<? extends IGenericPort>> findAccountUseCase) {
        this.findAccountUseCase = findAccountUseCase;
        this.mapper = new GenericMapper<>(FindAccountDTO.class);
    }

    @Override
    public void validate(IGenericPort param) throws GenericException {
        var findAccountParamDTO = mapper.map(param);
        var account = (Account) findAccountUseCase.exec(findAccountParamDTO).build();
        account.validatePassword(CryptoUtils.convertToSHA256(findAccountParamDTO.password()));
    }
}
