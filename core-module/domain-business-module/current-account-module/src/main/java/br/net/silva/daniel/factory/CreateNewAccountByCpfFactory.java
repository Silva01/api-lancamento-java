package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;

public class CreateNewAccountByCpfFactory implements IFactoryAggregate<Account, AccountDTO> {

    @Override
    public Account create(AccountDTO dto) {
        return new Account(dto.agency(), CryptoUtils.convertToSHA256("default"), dto.cpf());
    }
}
