package br.net.silva.business.validations;

import br.net.silva.business.exception.TransactionDuplicateException;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import br.net.silva.daniel.shared.application.interfaces.Validation;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public class DuplicateTransactionValidation implements Validation<BatchTransactionInput> {

    @Override
    public void validate(Optional<BatchTransactionInput> opt) throws GenericException {
        final var batchTransaction = opt.orElseThrow();
        final var quantityOfIdempotency = batchTransaction.batchTransaction().stream().map(TransactionInput::idempotencyId).distinct().count();
        if (batchTransaction.batchTransaction().size() != quantityOfIdempotency) {
            throw new TransactionDuplicateException("Transaction has 2 or more equals transactions.");
        }
    }
}
