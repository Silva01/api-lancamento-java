package br.net.silva.daniel.validation;

import br.net.silva.daniel.dto.ChangePasswordDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IGenericPort;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.mapper.GenericMapper;

public class ClientExistsValidate implements IValidations<IGenericPort> {

    private final GenericMapper<ChangePasswordDTO> mapper;
    private final UseCase<String, ClientDTO> findClientUseCase;

    public ClientExistsValidate(UseCase<String, ClientDTO> findClientUseCase) {
        this.findClientUseCase = findClientUseCase;
        this.mapper = new GenericMapper<>(ChangePasswordDTO.class);
    }

    @Override
    public void validate(IGenericPort param) throws GenericException {
        var changePasswordDTO = mapper.map(param);
        findClientUseCase.exec(changePasswordDTO.cpf());
    }
}
