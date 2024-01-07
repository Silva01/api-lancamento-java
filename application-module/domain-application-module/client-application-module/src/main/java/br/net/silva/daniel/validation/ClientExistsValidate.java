package br.net.silva.daniel.validation;

import br.net.silva.business.dto.ChangePasswordDTO;
import br.net.silva.daniel.entity.Client;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.mapper.GenericMapper;

import java.util.ArrayList;
import java.util.List;

public class ClientExistsValidate implements IValidations {

    private final GenericMapper<ChangePasswordDTO> mapper;
    private final UseCase<Client> findClientUseCase;

    public ClientExistsValidate(UseCase<Client> findClientUseCase) {
        this.findClientUseCase = findClientUseCase;
        this.mapper = new GenericMapper<>(ChangePasswordDTO.class);
    }

    @Override
    public void validate(IGenericPort param) throws GenericException {
        List<GenericException> errors = new ArrayList<>();

        try {
            findClientUseCase.exec(param);
        } catch (GenericException e) {
           errors.add(e);
        }

        if (errors.isEmpty()) {
            throw new GenericException("Client exists in database");
        }
    }
}
