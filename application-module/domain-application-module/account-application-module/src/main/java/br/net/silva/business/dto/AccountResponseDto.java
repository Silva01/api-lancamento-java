package br.net.silva.business.dto;

import br.net.silva.daniel.dto.AccountDTO;
import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;

import java.util.List;

public record AccountResponseDto(
        List<AccountDTO> accounts
) implements IGenericOutput {
}
