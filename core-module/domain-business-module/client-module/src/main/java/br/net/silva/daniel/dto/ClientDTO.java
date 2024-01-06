package br.net.silva.daniel.dto;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;

public class ClientDTO implements IGenericPort {

    private String id;
    private String cpf;
    private String name;
    private String telephone;
    private boolean active;
    private AddressDTO address;

    public ClientDTO(String id, String cpf, String name, String telephone, boolean active, AddressDTO address) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.telephone = telephone;
        this.active = active;
        this.address = address;
    }

    public ClientDTO() {
        this("", "", "", "", false, null);
    }

    public String id() {
        return id;
    }

    public String cpf() {
        return cpf;
    }

    public String name() {
        return name;
    }

    public String telephone() {
        return telephone;
    }

    public boolean active() {
        return active;
    }

    public AddressDTO address() {
        return address;
    }

    @Override
    public void accept(Class<?> clazz) {
        if (!clazz.isInstance(ClientDTO.class))
            throw new IllegalArgumentException("Class must be assignable from IAccountPort");
    }

    @Override
    public ClientDTO get() {
        return this;
    }
}
