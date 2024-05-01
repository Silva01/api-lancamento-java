package silva.daniel.project.app.domain.transaction.request;

import java.util.List;

public record TransactionBatchRequest(
        String cpf,
        Integer agency,
        Integer account,
        List<TransactionRequest> transactions
) {}
