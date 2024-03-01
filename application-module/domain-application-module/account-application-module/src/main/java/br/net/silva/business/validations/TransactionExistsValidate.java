package br.net.silva.business.validations;

import br.net.silva.business.exception.TransactionNotExistsException;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

import java.util.Optional;

public class TransactionExistsValidate implements IValidations {

    private final Repository<Optional<TransactionOutput>> transactionRepository;

    public TransactionExistsValidate(Repository<Optional<TransactionOutput>> transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void validate(Source param) throws GenericException {
        var input = (ReversalTransactionInput) param.input();
        var optionalTransaction = transactionRepository.exec(input.id(), input.idempotencyId());

        if (optionalTransaction.isEmpty()) {
            throw new TransactionNotExistsException("Transaction not found");
        }
    }
    
}
