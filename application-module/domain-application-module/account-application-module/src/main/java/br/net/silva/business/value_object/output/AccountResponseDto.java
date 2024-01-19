package br.net.silva.business.value_object.output;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.interfaces.Output;
import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public final class AccountResponseDto implements IGenericOutput, Output {
    private final List<AccountDTO> accounts;

    public AccountResponseDto(final List<AccountDTO> accounts) {
        this.accounts = accounts;
    }

    public AccountResponseDto() {
        this(new ArrayList<>());
    }
}
