package silva.daniel.project.app.domain.account.gateway;

import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.interfaces.IAccountParam;
import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import org.springframework.stereotype.Component;
import silva.daniel.project.app.domain.account.entity.Account;
import silva.daniel.project.app.domain.account.entity.AccountKey;
import silva.daniel.project.app.domain.account.entity.CreditCard;
import silva.daniel.project.app.domain.account.mapper.AccountMapper;
import silva.daniel.project.app.domain.account.repository.AccountRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class AccountGateway implements ApplicationBaseGateway<AccountOutput> {

    private final AccountRepository repository;

    public AccountGateway(AccountRepository repository) {
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

        if(param instanceof IAccountParam accountParam) {
            var key = new AccountKey(accountParam.accountNumber(), accountParam.agency());
            return repository.findById(key)
                    .map(AccountMapper::toOutput);
        }

        final var cpf = (ICpfParam) param;
        return repository.findByCpf(cpf.cpf())
                .map(AccountMapper::toOutput);
    }

    @Override
    public List<AccountOutput> findAllBy(ParamGateway param) {
        var cpfParam = (ICpfParam) param;
        return repository.findAllByCpf(cpfParam.cpf()).stream().map(AccountMapper::toOutput).toList();
    }

    @Override
    public AccountOutput save(AccountOutput entity) {
        var key = new AccountKey(entity.number(), entity.agency());
        CreditCard creditCard = null;
        if (entity.creditCard() != null) {
            creditCard = new CreditCard(entity.creditCard().number(), entity.creditCard().cvv(), entity.creditCard().flag(), entity.creditCard().balance(), entity.creditCard().expirationDate(), entity.creditCard().active());
        }

        var account = new Account(key, entity.balance(), entity.password(), entity.active(), entity.cpf(), creditCard, null, LocalDateTime.now());
        var newAccount = repository.save(account);
        return AccountMapper.toOutput(newAccount);
    }

    @Override
    public void saveAll(List<AccountOutput> paramList) {

    }
}
