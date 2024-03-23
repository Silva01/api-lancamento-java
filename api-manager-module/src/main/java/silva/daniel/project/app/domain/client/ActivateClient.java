package silva.daniel.project.app.domain.client;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ActivateClient(@NotNull @NotEmpty String cpf) {
}
