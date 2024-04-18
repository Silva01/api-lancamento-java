package silva.daniel.project.app.domain.account.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ChangePasswordRequest {
    @NotNull @Positive
    private Integer agency;

    @NotNull @Positive
    private Integer accountNumber;

    @NotBlank @Size(min = 11, max = 11)
    private String cpf;

    @NotBlank @Size(min = 6)
    private String password;

    @NotBlank @Size(min = 6)
    private String newPassword;
}
