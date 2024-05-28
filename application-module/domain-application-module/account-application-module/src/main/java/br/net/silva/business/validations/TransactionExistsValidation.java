package br.net.silva.business.validations;

import br.net.silva.business.exception.TransactionNotExistsException;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public class TransactionExistsValidation implements Validation<TransactionOutput> {

    @Override
    public void validate(Optional<TransactionOutput> opt) throws GenericException {
        if (opt.isEmpty()) {
            throw new TransactionNotExistsException("Transaction not found");
        }
    }
}
