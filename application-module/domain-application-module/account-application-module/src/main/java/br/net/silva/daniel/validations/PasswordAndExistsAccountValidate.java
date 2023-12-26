package br.net.silva.daniel.validations;

import br.net.silva.daniel.dto.ChangePasswordDTO;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;

public class PasswordAndExistsAccountValidate implements IValidations<ChangePasswordDTO> {
    @Override
    public void validate(ChangePasswordDTO param) throws GenericException {

    }
}
