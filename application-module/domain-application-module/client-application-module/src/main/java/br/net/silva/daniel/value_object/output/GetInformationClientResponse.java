package br.net.silva.daniel.value_object.output;

import br.net.silva.daniel.shared.application.interfaces.Output;
import lombok.Data;

@Data
public class GetInformationClientResponse implements Output {
    private String name;
    private String cpf;
    private String telephone;
    private String street;
    private String neighborhood;
    private String number;
    private String complement;
    private String state;
    private String city;
    private String zipCod;
}
