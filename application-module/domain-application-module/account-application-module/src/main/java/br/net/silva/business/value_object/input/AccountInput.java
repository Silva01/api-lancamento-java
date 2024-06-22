package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.IBasicAccountParam;
import br.net.silva.daniel.shared.application.interfaces.Input;

public record AccountInput(
        Integer accountNumber,
        Integer agency,
        String cpf
) implements Input, IBasicAccountParam {

    @Override
    public String toString() {
        return String.format("Account %d and agency %d:", accountNumber, agency);
    }
}
