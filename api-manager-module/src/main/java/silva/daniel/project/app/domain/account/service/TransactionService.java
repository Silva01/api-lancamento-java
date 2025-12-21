package silva.daniel.project.app.domain.account.service;

import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class TransactionService {

    private final TransactionFacade transactionFacade;

    @Autowired
    public TransactionService(TransactionFacade transactionFacade) {
        this.transactionFacade = transactionFacade;
    }

    public void registerTransaction(BatchTransactionInput input) throws DomainGenericException {
        transactionFacade.registerTransaction().exec(Source.of(input));
    }

    public void refundTransaction(ReversalTransactionInput input) throws DomainGenericException {
        transactionFacade.refundTransaction().exec(Source.of(input));
    }
}
