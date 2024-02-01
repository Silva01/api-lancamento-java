package br.net.silva.business.usecase;

import br.net.silva.daniel.dto.TransactionDTO;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.UseCase;
import br.net.silva.daniel.value_object.Source;

public class RegisterTransactionUseCase implements UseCase<TransactionDTO> {
    @Override
    public TransactionDTO exec(Source param) throws GenericException {
        return null;
    }
}
