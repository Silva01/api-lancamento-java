package silva.daniel.project.app.domain.transaction.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record TransactionBatchRequest(
        @NotBlank @Size(min = 11, max = 11) String cpf,
        @NotNull @Positive Integer agency,
        @NotNull @Positive Integer account,
        @Valid @Size(min = 1) List<TransactionRequest> transactions
) {}
