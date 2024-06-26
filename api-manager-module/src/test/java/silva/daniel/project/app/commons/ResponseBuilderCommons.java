package silva.daniel.project.app.commons;

import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.business.value_object.output.NewAccountResponse;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.dto.TransactionDTO;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static br.net.silva.business.enums.AccountStatusEnum.ACTIVE;
import static br.net.silva.daniel.enuns.TransactionTypeEnum.DEBIT;

public interface ResponseBuilderCommons {

    default GetInformationAccountOutput buildResponseMockAccountInformation() {
        final var mock = new GetInformationAccountOutput();
        mock.setAccountNumber(1234);
        mock.setAgency(1);
        mock.setBalance(BigDecimal.valueOf(1000));
        mock.setStatus(ACTIVE.name());
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

    default AccountsByCpfResponseDto buildAccounts() {
        return new AccountsByCpfResponseDto(List.of(
                new AccountDTO(1235, 1, BigDecimal.valueOf(1000), "test", true, "12345", Collections.emptyList(), null),
                new AccountDTO(1236, 1, BigDecimal.valueOf(1000), "test", false, "12345", Collections.emptyList(), null),
                new AccountDTO(1237, 1, BigDecimal.valueOf(1000), "test", false, "12345", Collections.emptyList(), null),
                new AccountDTO(1238, 1, BigDecimal.valueOf(1000), "test", false, "12345", Collections.emptyList(), null)
        ));
    }

    default NewAccountResponse buildNewAccountResponse() {
        final var response = new NewAccountResponse();
        response.setAccountNumber(1234);
        response.setAgency(1);
        return response;
    }


}
