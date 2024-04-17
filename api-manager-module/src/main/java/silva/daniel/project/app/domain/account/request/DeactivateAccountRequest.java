package silva.daniel.project.app.domain.account.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeactivateAccountRequest {
    @NotBlank @Size(min = 11, max = 11)
    private String cpf;

    @NotNull @Positive
    private Integer agency;

    @NotNull @Positive
    private Integer account;
}
