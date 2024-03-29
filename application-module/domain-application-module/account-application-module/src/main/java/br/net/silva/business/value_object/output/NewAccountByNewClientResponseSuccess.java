package br.net.silva.business.value_object.output;

import br.net.silva.daniel.shared.application.interfaces.Output;
import lombok.Data;

@Data
public class NewAccountByNewClientResponseSuccess implements Output {
    private Integer agency;
    private Integer accountNumber;
    private String provisionalPassword;
}
