package br.net.silva.daniel.factory;

import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;
import junit.framework.TestCase;

import java.math.BigDecimal;
import java.util.Collections;

public class CreateNewAccountByCpfFactoryTest extends TestCase {

    private CreateNewAccountByCpfFactory createNewAccountByCpfFactory;

    public void testShouldCreateNewAccountByCpfFactory() {
        createNewAccountByCpfFactory = new CreateNewAccountByCpfFactory();
        var account = buildMockAccount(true);

        var dto = account.build();
        var account2 = createNewAccountByCpfFactory.create(dto);
        assertNotNull(account2);

        var dto2 = account2.build();
        assertEquals(dto.agency(), dto2.agency());
        assertEquals(dto.cpf(), dto2.cpf());
        assertEquals(CryptoUtils.convertToSHA256("default"), dto2.password());
    }

    private Account buildMockAccount(boolean active) {
        return new Account(1, 45678, BigDecimal.valueOf(1000), CryptoUtils.convertToSHA256("978534"), active, "99988877766", null, Collections.emptyList());
    }

}