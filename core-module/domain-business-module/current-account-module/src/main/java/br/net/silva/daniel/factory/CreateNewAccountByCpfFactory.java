package br.net.silva.daniel.factory;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.entity.Account;
import br.net.silva.daniel.shared.business.factory.IFactoryAggregate;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.mapper.GenericMapper;
import br.net.silva.daniel.shared.business.utils.CryptoUtils;

public class CreateNewAccountByCpfFactory implements IFactoryAggregate<Account, IGenericPort> {

    private final GenericMapper<AccountDTO> mapper;

    public CreateNewAccountByCpfFactory() {
        this.mapper = new GenericMapper<>(AccountDTO.class);
    }

    @Override
    public Account create(IGenericPort accountPord) {
        var accountDTO = mapper.map(accountPord);
        return new Account(accountDTO.bankAgencyNumber(), CryptoUtils.convertToSHA256("default"), accountDTO.cpf());
    }
}
