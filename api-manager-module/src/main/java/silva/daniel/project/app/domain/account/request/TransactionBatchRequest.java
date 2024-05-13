package silva.daniel.project.app.domain.account.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TransactionBatchRequest(
        @Valid @NotNull AccountTransactionRequest source,
        @Valid @NotNull AccountTransactionRequest destiny,
        @Valid @Size(min = 1) List<TransactionRequest> transactions
) {}
