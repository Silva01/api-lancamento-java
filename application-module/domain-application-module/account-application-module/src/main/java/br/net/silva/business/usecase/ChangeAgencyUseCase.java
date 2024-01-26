package br.net.silva.business.usecase;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.value_object.Source;

public class ChangeAgencyUseCase implements UseCase<AccountDTO> {
    @Override
    public AccountDTO exec(Source param) throws GenericException {
        return null;
    }
}
