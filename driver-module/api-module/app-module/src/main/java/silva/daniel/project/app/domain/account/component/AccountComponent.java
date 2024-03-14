package silva.daniel.project.app.domain.account.component;

import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import org.springframework.stereotype.Component;
import silva.daniel.project.app.domain.account.entity.Account;
import silva.daniel.project.app.domain.account.entity.AccountKey;
import silva.daniel.project.app.domain.account.repository.AccountRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class AccountComponent implements ApplicationBaseGateway<AccountOutput> {

    private final AccountRepository repository;

    public AccountComponent(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean deleteById(ParamGateway param) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public Optional<AccountOutput> findById(ParamGateway param) {
        return Optional.empty();
    }

    @Override
    public List<AccountOutput> findAll() {
        return null;
    }

    @Override
    public AccountOutput save(AccountOutput entity) {
        var key = new AccountKey(entity.number(), entity.agency());
        var account = new Account(key, entity.balance(), entity.password(), entity.active(), entity.cpf(), null, null);
        var newAccount = repository.save(account);
        return new AccountOutput(newAccount.getKeys().getNumber(), newAccount.getKeys().getBankAgencyNumber(), newAccount.getBalance(), newAccount.getPassword(), newAccount.isActive(), newAccount.getCpf(), null, Collections.emptyList());
    }

    @Override
    public void saveAll(List<AccountOutput> paramList) {

    }
}
