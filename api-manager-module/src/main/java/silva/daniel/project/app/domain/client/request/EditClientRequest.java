package silva.daniel.project.app.domain.client.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EditClientRequest (
        @NotNull @NotEmpty String cpf,
        @NotNull @NotEmpty String name,
        String telephone
) {
}
