package silva.daniel.project.app.domain.account.mapper;

import br.net.silva.business.value_object.output.AccountOutput;
import silva.daniel.project.app.domain.account.entity.Account;

public interface AccountMapper {
    static AccountOutput toOutput(Account entity) {
        return new AccountOutput(
                entity.getKeys().getNumber(),
                entity.getKeys().getBankAgencyNumber(),
                entity.getBalance(),
                entity.getPassword(),
                entity.isActive(),
                entity.getCpf(),
                CreditCardMapper.toOutput(entity.getCreditCard()),
                TransactionMapper.convert(entity.getTransactions()).map(TransactionMapper.convert()).toList()
        );
    }
}
