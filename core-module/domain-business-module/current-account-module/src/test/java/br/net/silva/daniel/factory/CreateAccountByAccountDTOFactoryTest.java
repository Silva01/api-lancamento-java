package br.net.silva.daniel.factory;

import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.entity.CreditCard;
import br.net.silva.daniel.entity.Transaction;
import br.net.silva.daniel.enuns.FlagEnum;
import br.net.silva.daniel.enuns.TransactionTypeEnum;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class CreateAccountByAccountDTOFactoryTest extends TestCase {

    private CreateAccountByAccountDTOFactory createAccountByAccountDTOFactory;

    public void testShouldCreateAccountWithSuccess() {
        createAccountByAccountDTOFactory = new CreateAccountByAccountDTOFactory();
        var account = buildMockAccount(true);
        var dto = account.build();

        var account2 = createAccountByAccountDTOFactory.create(dto);
        assertNotNull(account2);
        assertEquals(dto, account2.build());

    }

    private Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", buildMockCreditCard(true), buildListTransaction());
    }

    private List<Transaction> buildListTransaction() {
        return List.of(
                new Transaction(1L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(2L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(3L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(4L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(5L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(6L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(7L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(8L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(9L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321),
                new Transaction(10L, "test", BigDecimal.valueOf(100), 1, TransactionTypeEnum.CREDIT, 333, 222, 4444L, "00000", 321)
        );
    }

    private CreditCard buildMockCreditCard(boolean activate) {
        return new CreditCard("99988877766", 45678, FlagEnum.MASTER_CARD, BigDecimal.valueOf(1000), LocalDate.of(2027, 1, 1), activate);
    }
}