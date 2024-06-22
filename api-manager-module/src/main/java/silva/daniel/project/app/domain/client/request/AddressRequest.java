package silva.daniel.project.app.domain.client.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank String cpf,
        @NotBlank String street,
        @NotBlank String number,
        String complement,
        @NotBlank String neighborhood,
        @NotBlank String state,
        @NotBlank String city,
        @NotBlank String zipCode
) {
}
