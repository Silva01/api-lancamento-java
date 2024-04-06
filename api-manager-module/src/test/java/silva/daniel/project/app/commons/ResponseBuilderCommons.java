package silva.daniel.project.app.commons;

import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.daniel.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.List;

import static br.net.silva.business.enums.AccountStatusEnum.ACTIVE;
import static br.net.silva.daniel.enuns.TransactionTypeEnum.DEBIT;

public interface ResponseBuilderCommons {

    default GetInformationAccountOutput buildResponseMockAccountInformation() {
        final var mock = new GetInformationAccountOutput();
        mock.setAccountNumber(1234);
        mock.setAgency(1);
        mock.setBalance(BigDecimal.valueOf(1000));
        mock.setStatus(ACTIVE);
        mock.setTransactions(List.of(
                new TransactionDTO(
                        1L,
                        "test",
                        BigDecimal.valueOf(100),
                        1,
                        DEBIT,
                        1234,
                        5678,
                        123L,
                        null,
                        null
                )
        ));

        return mock;
    }


}
