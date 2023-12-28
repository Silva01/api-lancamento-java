package br.net.silva.daniel.validations;

import br.net.silva.daniel.dto.ChangePasswordDTO;
import br.net.silva.daniel.dto.FindAccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IGenericPort;
import br.net.silva.daniel.interfaces.IProcessResponse;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.utils.CryptoUtils;

public class PasswordAndExistsAccountValidate implements IValidations<ChangePasswordDTO> {

    private final UseCase<FindAccountDTO, IProcessResponse<? extends IGenericPort>> findAccountUseCase;

    public PasswordAndExistsAccountValidate(UseCase<FindAccountDTO, IProcessResponse<? extends IGenericPort>> findAccountUseCase) {
        this.findAccountUseCase = findAccountUseCase;
    }

    @Override
    public void validate(ChangePasswordDTO param) throws GenericException {
        var findAccountParamDTO = new FindAccountDTO(param.cpf(), param.account(), param.agency());
        var account = (Account) findAccountUseCase.exec(findAccountParamDTO).build();
        account.validatePassword(CryptoUtils.convertToSHA256(param.get().oldPassword()));
    }
}
