package br.net.silva.daniel.factory;

import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.interfaces.ICreateAccountPord;

public class CreateNewAccountByCpfFactory implements IFactoryAggregate<Account, ICreateAccountPord> {

    @Override
    public Account create(ICreateAccountPord accountPord) {
        return new Account(accountPord.bankAgencyNumber(), accountPord.password(), accountPord.cpf());
    }
}
