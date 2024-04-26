package silva.daniel.project.app.domain.account.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record NewAccountRequest(
        @NotBlank @Size(min = 11, max = 11) String cpf,
        @Positive @NotNull Integer agencyNumber,
        @NotBlank @Size(min = 6) String password
) {}
