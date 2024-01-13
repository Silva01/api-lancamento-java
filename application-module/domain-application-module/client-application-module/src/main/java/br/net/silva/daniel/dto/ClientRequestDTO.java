package br.net.silva.daniel.dto;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public class ClientRequestDTO implements IGenericPort {

    private String id;
    private String cpf;
    private String name;
    private String telephone;
    private boolean active;
    private Integer agency;
    private AddressRequestDTO addressRequestDTO;

    public ClientRequestDTO(String id, String cpf, String name, String telephone, boolean active, Integer agency, AddressRequestDTO addressRequestDTO) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.telephone = telephone;
        this.active = active;
        this.agency = agency;
        this.addressRequestDTO = addressRequestDTO;
    }

    public ClientRequestDTO() {
        this(null, null, null, null, false, null, null);
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

    public Integer agencyNumber() {
        return agency;
    }

    public AddressRequestDTO addressRequestDTO() {
        return addressRequestDTO;
    }

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, ClientRequestDTO.class, "Type of class is not ClientRequestDTO");
    }

    @Override
    public Object get() {
        return this;
    }
}
