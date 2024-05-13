package br.net.silva.business.usecase;

import br.net.silva.business.validations.DuplicateTransactionValidation;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.SaveApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

@ValidateStrategyOn(validations = { DuplicateTransactionValidation.class })
public class RegisterTransactionUseCase implements UseCase<BatchTransactionInput> {

    private final SaveApplicationBaseGateway<BatchTransactionInput> saveTransactionRepository;

    public RegisterTransactionUseCase(SaveApplicationBaseGateway<BatchTransactionInput> saveTransactionRepository) {
        this.saveTransactionRepository = saveTransactionRepository;
    }

    @Override
    public BatchTransactionInput exec(Source param) throws GenericException {
        final var input = ((BatchTransactionInput) param.input());
        execValidate(Optional.of(input));
        return saveTransactionRepository.save(input);
    }
}
