package silva.daniel.project.app.web;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.business.value_object.input.TransactionInput;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import silva.daniel.project.app.domain.account.request.TransactionBatchRequest;
import silva.daniel.project.app.domain.account.service.TransactionService;

@RestController
@RequestMapping("/api/transaction")
public final class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@Valid @RequestBody TransactionBatchRequest request) throws Exception {
        final var input = new BatchTransactionInput(
                new AccountInput(request.source().account(), request.source().agency(), request.source().cpf()),
                new AccountInput(request.destiny().account(), request.destiny().agency(), request.destiny().cpf()),
        request.transactions().stream().map(transaction -> new TransactionInput(
                transaction.id().longValue(),
                transaction.description(),
                transaction.price(),
                transaction.quantity(),
                transaction.type(),
                transaction.idempotencyId(),
                transaction.creditCardNumber(),
                transaction.creditCardCvv()
        )).toList());

        transactionService.registerTransaction(input);
    }
}
