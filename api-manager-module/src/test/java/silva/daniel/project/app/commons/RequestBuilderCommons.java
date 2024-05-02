package silva.daniel.project.app.commons;

import br.net.silva.daniel.enuns.TransactionTypeEnum;
import silva.daniel.project.app.domain.account.request.ActivateAccountRequest;
import silva.daniel.project.app.domain.account.request.CreateCreditCardRequest;
import silva.daniel.project.app.domain.account.request.DeactivateAccountRequest;
import silva.daniel.project.app.domain.account.request.DeactivateCreditCardRequest;
import silva.daniel.project.app.domain.account.request.EditAgencyOfAccountRequest;
import silva.daniel.project.app.domain.account.request.ChangePasswordRequest;
import silva.daniel.project.app.domain.account.request.NewAccountRequest;
import silva.daniel.project.app.domain.transaction.request.AccountTransactionRequest;
import silva.daniel.project.app.domain.transaction.request.TransactionBatchRequest;
import silva.daniel.project.app.domain.transaction.request.TransactionRequest;

import java.math.BigDecimal;
import java.util.List;

public interface RequestBuilderCommons {

    default DeactivateCreditCardRequest buildBaseRequest() {
        return new DeactivateCreditCardRequest(
                "12345678901",
                123456,
                1234,
                "1234567890123456");
    }

    default CreateCreditCardRequest buildBaseCreateCreditCardRequest() {
        return new CreateCreditCardRequest("12345678901", 123456, 1234);
    }

    default EditAgencyOfAccountRequest buildBaseEditAgencyOfAccountRequest() {
        return new EditAgencyOfAccountRequest("12345678901", 123456, 1234, 3333);
    }

    default ActivateAccountRequest buildBaseActivateAccount() {
        return new ActivateAccountRequest("12345678901", 1, 1234);
    }

    default DeactivateAccountRequest buildBaseDeactivateAccount() {
        return new DeactivateAccountRequest("12345678901", 123456, 1234);
    }

    default ChangePasswordRequest buildBaseCreateNewPasswordForAccount() {
        return ChangePasswordRequest
                .builder()
                .agency(1)
                .accountNumber(1234)
                .cpf("12345678901")
                .password("123456")
                .newPassword("876543")
                .build();
    }

    default NewAccountRequest buildBaseNewAccountRequest() {
        return new NewAccountRequest("12345678901", 123456, "123444");
    }

    default TransactionBatchRequest buildBaseTransactionDebitBatchRequest() {
        return new TransactionBatchRequest(
                new AccountTransactionRequest("99988877700",
                                              1,
                                              12345),
                new AccountTransactionRequest("99900099901",
                                              1,
                                              12346),
                List.of(new TransactionRequest(
                        123,
                        "Compra no debito",
                        BigDecimal.valueOf(100),
                        1,
                        TransactionTypeEnum.DEBIT,
                        123L,
                        null,
                        null
                ))
        );
    }
}
