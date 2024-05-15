package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import silva.daniel.project.app.domain.account.request.RefundRequest;
import silva.daniel.project.app.service.FluxService;

@Service
public final class TransactionService {

    private final FluxService fluxService;

    @Autowired
    public TransactionService(FluxService fluxService) {
        this.fluxService = fluxService;
    }

    public void registerTransaction(BatchTransactionInput input) throws GenericException {
        fluxService.fluxRegisterTransaction().exec(Source.of(input));
    }

    public void refundTransaction(ReversalTransactionInput input) throws GenericException {
    }
}
