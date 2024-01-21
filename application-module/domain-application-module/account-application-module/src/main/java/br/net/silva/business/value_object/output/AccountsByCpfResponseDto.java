package br.net.silva.business.value_object.output;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.interfaces.Output;
import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class AccountsByCpfResponseDto implements IGenericOutput, Output {
    private final List<AccountDTO> accounts;

    public AccountsByCpfResponseDto(final List<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public AccountsByCpfResponseDto() {
        this(new ArrayList<>());
    }
}
