package silva.daniel.project.app.domain.transaction.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TransactionBatchRequest(
        @Valid AccountTransactionRequest source,
        @Valid AccountTransactionRequest destiny,
        @Valid @Size(min = 1) List<TransactionRequest> transactions
) {}
