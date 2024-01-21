package br.net.silva.business.mapper;

import br.net.silva.business.value_object.output.NewAccountResponse;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.interfaces.IMapperResponse;
import br.net.silva.daniel.interfaces.Output;
import org.springframework.stereotype.Component;

@Component
public class CreateResponseToNewAccountFactory implements IMapperResponse<AccountDTO, Output> {
    @Override
    public boolean accept(Object input, Output output) {
        if (input == null || output == null) {
            return false;
        }

        return input instanceof AccountDTO && output instanceof NewAccountResponse;
    }

    @Override
    public void toFillIn(AccountDTO input, Output output) {
        ((NewAccountResponse) output).setAgency(input.agency());
        ((NewAccountResponse) output).setAccountNumber(input.number());
    }
}
