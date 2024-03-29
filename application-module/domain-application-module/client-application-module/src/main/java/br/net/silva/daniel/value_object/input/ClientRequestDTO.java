package br.net.silva.daniel.value_object.input;

import br.net.silva.daniel.shared.application.interfaces.IAddressParam;
import br.net.silva.daniel.shared.application.interfaces.IClientParam;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public record ClientRequestDTO(
    String id,
    String cpf,
    String name,
    String telephone,
    boolean active,
    Integer agency,
    AddressRequestDTO addressRequestDTO
) implements IGenericPort, IClientParam {

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, ClientRequestDTO.class, "Type of class is not ClientRequestDTO");
    }

    @Override
    public Object get() {
        return this;
    }

    @Override
    public IAddressParam address() {
        return addressRequestDTO;
    }
}
