package br.net.silva.business.mapper;

import br.net.silva.business.value_object.output.AccountsByCpfResponseDto;
import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.interfaces.IMapperResponse;
import br.net.silva.daniel.interfaces.Output;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreateResponseToFindAccountsByCpfFactory implements IMapperResponse<List<AccountDTO>, Output> {
    @Override
    public boolean accept(List<AccountDTO> input, Output output) {
        if (input == null || output == null) {
            return false;
        }

        return output instanceof AccountsByCpfResponseDto;
    }

    @Override
    public void toFillIn(List<AccountDTO> input, Output output) {
        ((AccountsByCpfResponseDto) output).getAccounts().addAll(input);
    }
}
