package br.net.silva.business.validations;

import br.net.silva.business.utils.CryptoUtils;
import br.net.silva.business.dto.FindAccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.*;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IMapper;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;
import br.net.silva.daniel.shared.business.mapper.GenericMapper;

public class PasswordAndExistsAccountValidate implements IValidations {

    private final UseCase<IProcessResponse<? extends IGenericPort>> findAccountUseCase;
    private final IMapper<FindAccountDTO, IGenericPort> mapper;

    public PasswordAndExistsAccountValidate(UseCase<IProcessResponse<? extends IGenericPort>> findAccountUseCase) {
        this.findAccountUseCase = findAccountUseCase;
        this.mapper = new GenericMapper<>(FindAccountDTO.class);
    }

    @Override
    public void validate(IGenericPort param) throws GenericException {
        var findAccountParamDTO = this.mapper.map(param);
        var account = (Account) findAccountUseCase.exec(findAccountParamDTO).build();
        account.validatePassword(CryptoUtils.convertToSHA256(findAccountParamDTO.password()));
    }
}
