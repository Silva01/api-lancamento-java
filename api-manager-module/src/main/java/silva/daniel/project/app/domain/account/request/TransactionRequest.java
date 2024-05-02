package silva.daniel.project.app.domain.account.request;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import silva.daniel.project.app.annotation.ConditionalCreditCardValidation;

import java.math.BigDecimal;

@ConditionalCreditCardValidation(
        selected = "type",
        required = {"creditCardNumber", "creditCardCvv"},
        values = {"CREDIT"})
public record TransactionRequest(
        @NotNull @Positive Integer id,
        String description,
        @NotNull @Positive BigDecimal price,
        @NotNull @Positive Integer quantity,
        @NotNull TransactionTypeEnum type,
        @NotNull @Positive Long idempotencyId,
        String creditCardNumber,
        Integer creditCardCvv
) {}
