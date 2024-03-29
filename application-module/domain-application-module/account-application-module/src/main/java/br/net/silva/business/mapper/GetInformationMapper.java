package br.net.silva.business.mapper;

import br.net.silva.business.enums.AccountStatusEnum;
import br.net.silva.business.value_object.output.GetInformationAccountOutput;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.shared.application.interfaces.IMapperResponse;
import br.net.silva.daniel.shared.application.interfaces.Output;

import java.util.Objects;

public class GetInformationMapper implements IMapperResponse<AccountDTO, Output> {
    @Override
    public boolean accept(Object input, Output output) {
        return input instanceof AccountDTO && output instanceof GetInformationAccountOutput;
    }

    @Override
    public void toFillIn(AccountDTO input, Output output) {
        ((GetInformationAccountOutput) output).setAgency(input.agency());
        ((GetInformationAccountOutput) output).setAccountNumber(input.number());
        ((GetInformationAccountOutput) output).setBalance(input.balance());
        ((GetInformationAccountOutput) output).setStatus(input.active() ? AccountStatusEnum.ACTIVE : AccountStatusEnum.INACTIVE);
        ((GetInformationAccountOutput) output).setHaveCreditCard(Objects.nonNull(input.creditCard()));
        ((GetInformationAccountOutput) output).setTransactions(input.transactions());
    }
}
