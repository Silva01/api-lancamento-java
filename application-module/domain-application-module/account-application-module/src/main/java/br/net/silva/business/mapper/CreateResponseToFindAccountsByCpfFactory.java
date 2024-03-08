package br.net.silva.business.mapper;

import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.interfaces.IMapperResponse;
import br.net.silva.daniel.interfaces.Output;

import java.util.List;

public class CreateResponseToFindAccountsByCpfFactory implements IMapperResponse<List<AccountDTO>, Output> {
    @Override
    public boolean accept(Object input, Output output) {
        if (input == null || output == null) {
            return false;
        }

        Object obj;
        if (input instanceof List<?> list && !list.isEmpty()) {
            obj = list.get(0);
            return obj instanceof AccountDTO && output instanceof AccountsByCpfResponseDto;
        }

        if (input instanceof List<?>) {
            return output instanceof AccountsByCpfResponseDto;
        }

        return false;
    }

    @Override
    public void toFillIn(List<AccountDTO> input, Output output) {
        ((AccountsByCpfResponseDto) output).getAccounts().addAll(input);
    }
}
