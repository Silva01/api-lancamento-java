package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.commons;

import br.net.silva.business.value_object.input.AccountInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.AccountKey;
import net.datafaker.Faker;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public class Fixture {

    private final TestEntityManager testEntityManager;
    private final Faker faker;

    public Fixture(TestEntityManager testEntityManager) {
        this.testEntityManager = testEntityManager;
        this.faker = new Faker();
    }

    public AccountInput createAccount() {
        final var account = new Account();
        account.setCpf(faker.cpf().valid());
        account.setActive(true);
        account.setBalance(BigDecimal.valueOf(3000));
        account.setKeys(new AccountKey(faker.number().numberBetween(0, 200), faker.number().randomDigit()));
        account.setCreatedAt(LocalDateTime.now());

        final var newAccount = testEntityManager.persistAndFlush(account);
        return new AccountInput(newAccount.getKeys().getNumber(), newAccount.getKeys().getBankAgencyNumber(), newAccount.getCpf());
    }
}
