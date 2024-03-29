package silva.daniel.project.app.domain.account.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DeactivateCreditCardRequest(
        @NotBlank @Size(min = 11, max = 11) String cpf,
        @NotNull Integer accountNumber,
        @NotNull Integer agency,
        @NotBlank @Size(min = 14, max = 16) String creditCardNumber
) {
}
