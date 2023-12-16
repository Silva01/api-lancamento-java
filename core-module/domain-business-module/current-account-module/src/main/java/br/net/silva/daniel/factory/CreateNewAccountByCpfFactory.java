package br.net.silva.daniel.factory;

import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.interfaces.IAccountPord;

public class CreateNewAccountByCpfFactory implements IFactoryAggregate<Account, IAccountPord> {

    @Override
    public Account create(IAccountPord accountPord) {
        return new Account(accountPord.bankAgencyNumber(), "123456", accountPord.cpf());
    }
}
