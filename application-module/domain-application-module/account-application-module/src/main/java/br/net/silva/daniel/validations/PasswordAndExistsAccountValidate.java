package br.net.silva.daniel.validations;

import br.net.silva.daniel.dto.FindAccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IGenericPort;
import br.net.silva.daniel.interfaces.IProcessResponse;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericMapper;
import br.net.silva.daniel.utils.CryptoUtils;

public class PasswordAndExistsAccountValidate implements IValidations<IGenericPort> {

    private final UseCase<FindAccountDTO, IProcessResponse<? extends IGenericPort>> findAccountUseCase;
    private final GenericMapper<FindAccountDTO> mapper;

    public PasswordAndExistsAccountValidate(UseCase<FindAccountDTO, IProcessResponse<? extends IGenericPort>> findAccountUseCase) {
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
