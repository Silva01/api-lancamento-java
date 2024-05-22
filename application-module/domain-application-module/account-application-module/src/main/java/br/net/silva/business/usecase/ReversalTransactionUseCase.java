package br.net.silva.business.usecase;

import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

public class ReversalTransactionUseCase implements UseCase<ReversalTransactionInput> {

    private final ApplicationBaseGateway<ReversalTransactionInput> baseGateway;

    public ReversalTransactionUseCase(ApplicationBaseGateway<ReversalTransactionInput> baseGateway) {
        this.baseGateway = baseGateway;
    }

    @Override
    public ReversalTransactionInput exec(Source param) throws GenericException {
        var input = (ReversalTransactionInput) param.input();
        baseGateway.save(input);

        return input;
    }
}
