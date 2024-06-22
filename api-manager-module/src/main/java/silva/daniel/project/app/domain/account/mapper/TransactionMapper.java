package silva.daniel.project.app.domain.account.mapper;

import br.net.silva.business.value_object.output.TransactionOutput;
import silva.daniel.project.app.domain.account.entity.Transaction;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public interface TransactionMapper {
    static Stream<Transaction> convert(List<Transaction> entities) {

        if (entities == null) {
            return Stream.empty();
        }

        return entities.stream();
    }

    static Function<Transaction, TransactionOutput> convert() {
        return transaction -> new TransactionOutput(
                transaction.getId(),
                transaction.getDescription(),
                transaction.getPrice(),
                transaction.getQuantity(),
                transaction.getType(),
                transaction.getOriginAccountNumber().getKeys().getNumber(),
                transaction.getDestinationAccountNumber().getKeys().getNumber(),
                transaction.getIdempotencyId(),
                transaction.getCreditCardNumber().getNumber(),
                transaction.getCreditCardNumber().getCvv()
        );
    }
}
