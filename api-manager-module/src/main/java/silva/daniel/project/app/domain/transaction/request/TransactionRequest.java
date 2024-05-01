package silva.daniel.project.app.domain.transaction.request;

import br.net.silva.daniel.enuns.TransactionTypeEnum;

import java.math.BigDecimal;

public record TransactionRequest(
        Integer id,
        String description,
        BigDecimal price,
        Integer quantity,
        TransactionTypeEnum type,
        Long idempotencyId,
        String creditCardNumber,
        Integer creditCardCvv
) {}
