package br.net.silva.business.validations;

import java.util.Optional;

import br.net.silva.business.exception.TransactionNotExistsException;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.repository.Repository;
import br.net.silva.daniel.value_object.Source;

public class TransactionExistsValidate implements IValidations {

    private final Repository<Optional<Transaction>> transactionRepository;

    public TransactionExistsValidate(Repository<Optional<Transaction>> transactionRepository) {
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
