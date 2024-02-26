package br.net.silva.daniel.factory;

import br.net.silva.daniel.interfaces.ClientFactorySpec;
import br.net.silva.daniel.value_object.output.AddressOutput;
import br.net.silva.daniel.value_object.output.ClientOutput;

public final class ClientOutputFactory implements ClientFactorySpec.BuildSpec<ClientOutput> {

    private String id;
    private String cpf;
    private String name;
    private String telephone;
    private boolean active;
    private AddressOutput address;

    public static ClientFactorySpec.CpfSpec<ClientOutput> createOutput() {
        return new ClientOutputFactory();
    }

    @Override
    public ClientFactorySpec.NameSpec<ClientOutput> withCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public ClientFactorySpec.ActiveSpec<ClientOutput> withName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public ClientFactorySpec.TelephoneSpec<ClientOutput> withAddress(AddressOutput address) {
        this.address = address;
        return this;
    }

    @Override
    public ClientFactorySpec.AddressSpec<ClientOutput> withActive(boolean active) {
        this.active = active;
        return this;
    }

    @Override
    public ClientFactorySpec.BuildSpec<ClientOutput> andWithActive(boolean active) {
        withActive(active);
        return this;
    }

    @Override
    public ClientFactorySpec.IdSpec<ClientOutput> withTelephone(String telephone) {
        this.telephone = telephone;
        return this;
    }

    @Override
    public ClientFactorySpec.BuildSpec<ClientOutput> andWithTelephone(String telephone) {
        withTelephone(telephone);
        return this;
    }

    @Override
    public ClientFactorySpec.BuildSpec<ClientOutput> andWithId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public ClientOutput build() {
        return new ClientOutput(id, cpf, name, telephone, active, address);
    }
}
