package silva.daniel.project.app.domain.account.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ActivateAccountRequest(
        @NotBlank @Size(min = 11, max = 11) String cpf,
        @NotNull @Positive Integer agency,
        @NotNull @Positive Integer accountNumber
) {
}
