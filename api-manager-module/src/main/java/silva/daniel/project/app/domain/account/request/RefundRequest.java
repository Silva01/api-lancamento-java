package silva.daniel.project.app.domain.account.request;

public record RefundRequest(
        String cpf,
        Long transactionId,
        Long idempotencyId
) {}
