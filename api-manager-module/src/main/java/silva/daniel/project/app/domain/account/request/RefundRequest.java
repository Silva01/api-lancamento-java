package silva.daniel.project.app.domain.account.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record RefundRequest(
        @NotBlank @Size(min = 11, max = 11) String cpf,
        @Positive Long transactionId,
        @Positive Long idempotencyId
) {}
