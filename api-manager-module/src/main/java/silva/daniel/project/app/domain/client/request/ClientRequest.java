package silva.daniel.project.app.domain.client.request;

import br.net.silva.daniel.value_object.input.AddressRequestDTO;
import jakarta.validation.constraints.NegativeOrZero;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ClientRequest(
        String id,
        @NotEmpty String cpf,
        String name,
        String telephone,
        boolean active,
        @Positive @NotNull Integer agency,
        @NotNull AddressRequestDTO addressRequestDTO
) {
}
