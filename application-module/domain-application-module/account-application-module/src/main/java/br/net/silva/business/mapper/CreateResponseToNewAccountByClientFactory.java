package br.net.silva.business.mapper;

import br.net.silva.business.value_object.output.NewAccountByNewClientResponseSuccess;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.interfaces.IMapperResponse;
import br.net.silva.daniel.interfaces.Output;
import org.springframework.stereotype.Component;

@Component
public class CreateResponseToNewAccountByClientFactory implements IMapperResponse<AccountDTO, Output> {
    @Override
    public boolean accept(Object input, Output output) {
        return input instanceof AccountDTO && output instanceof NewAccountByNewClientResponseSuccess;
    }

    @Override
    public void toFillIn(AccountDTO input, Output output) {
        ((NewAccountByNewClientResponseSuccess) output).setAccountNumber(input.number());
        ((NewAccountByNewClientResponseSuccess) output).setAgency(input.agency());
        ((NewAccountByNewClientResponseSuccess) output).setProvisionalPassword(input.password());
    }
}
