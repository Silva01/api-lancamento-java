package br.net.silva.daniel.factory;

import br.net.silva.daniel.build.AddressBuilder;
import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.interfaces.ClientFactorySpec;
import br.net.silva.daniel.value_object.output.AddressOutput;

public final class ClientDtoFactory implements ClientFactorySpec.BuildSpec<ClientDTO> {

    private String id;
    private String cpf;
    private String name;
    private String telephone;
    private boolean active;
    private AddressDTO address;

    public static ClientFactorySpec.CpfSpec<ClientDTO> createDto() {
        return new ClientDtoFactory();
    }

    @Override
    public ClientFactorySpec.NameSpec<ClientDTO> withCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public ClientFactorySpec.ActiveSpec<ClientDTO> withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ClientFactorySpec.TelephoneSpec<ClientDTO> withAddress(AddressOutput address) {
        this.address = AddressBuilder.buildFullAddressDto().createFrom(address);
        return this;
    }

    @Override
    public ClientFactorySpec.AddressSpec<ClientDTO> withActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public ClientFactorySpec.BuildSpec<ClientDTO> andWithActive(boolean active) {
        withActive(active);
        return this;
    }

    @Override
    public ClientFactorySpec.IdSpec<ClientDTO> withTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    @Override
    public ClientFactorySpec.BuildSpec<ClientDTO> andWithTelephone(String telephone) {
        withTelephone(telephone);
        return this;
    }

    @Override
    public ClientFactorySpec.BuildSpec<ClientDTO> andWithId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public ClientDTO build() {
        return new ClientDTO(id, cpf, name, telephone, active, address);
    }
}
