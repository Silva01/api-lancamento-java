package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;

public class CreateNewAccountByCpfFactory implements IFactoryAggregate<Account, AccountDTO> {
    @Override
    public Account create(AccountDTO accountDTO) {
        return new Account(accountDTO.bankAgencyNumber(), accountDTO.password(), accountDTO.cpf());
    }
}
