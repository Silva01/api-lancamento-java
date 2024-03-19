package silva.daniel.project.app.commons;

import br.net.silva.business.value_object.output.AccountOutput;
import silva.daniel.project.app.domain.account.entity.Account;
import silva.daniel.project.app.domain.account.entity.AccountKey;

import java.math.BigDecimal;

public interface AccountCommons {

    static AccountOutput createAccountOutput() {
        return new AccountOutput(
                1,
                2,
                BigDecimal.valueOf(2000),
                "test",
                true,
                "00099988877",
                null,
                null
        );
    }

    static Account createEntity() {
        return new Account(
                new AccountKey(1, 2),
                BigDecimal.valueOf(2000),
                "test",
                true,
                "00099988877",
                null,
                null
        );
    }
}
