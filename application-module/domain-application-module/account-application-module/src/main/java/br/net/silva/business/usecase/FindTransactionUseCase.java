package br.net.silva.business.usecase;

import br.net.silva.business.validations.TransactionExistsValidation;
import br.net.silva.business.value_object.output.TransactionOutput;
import br.net.silva.daniel.shared.application.annotations.ValidateStrategyOn;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.ITransactionParam;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

@ValidateStrategyOn(validations = { TransactionExistsValidation.class })
public final class FindTransactionUseCase implements UseCase<TransactionOutput> {

    private final ApplicationBaseGateway<TransactionOutput> baseGateway;

    public FindTransactionUseCase(ApplicationBaseGateway<TransactionOutput> baseGateway) {
        this.baseGateway = baseGateway;
    }

    @Override
    public TransactionOutput exec(Source param) throws GenericException {
        final var input = ((ITransactionParam) param.input());
        final var transactionOpt = baseGateway.findById(input);
        return execValidate(transactionOpt).extract();
    }
}
