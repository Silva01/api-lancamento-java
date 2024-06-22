package br.net.silva.business.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.ICpfParam;
import br.net.silva.daniel.shared.application.interfaces.Input;

public record GetInformationAccountInput(
        String cpf
) implements ICpfParam, Input {

}
