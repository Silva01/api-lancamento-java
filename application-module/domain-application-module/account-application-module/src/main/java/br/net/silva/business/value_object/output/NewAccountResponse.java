package br.net.silva.business.value_object.output;

import br.net.silva.daniel.interfaces.Output;
import lombok.Data;

@Data
public final class NewAccountResponse implements Output {
    private Integer agency;
    private Integer accountNumber;

    public NewAccountResponse(Integer agency, Integer accountNumber) {
        this.agency = agency;
        this.accountNumber = accountNumber;
    }

    public NewAccountResponse() {
        this(null, null);
    }
}
