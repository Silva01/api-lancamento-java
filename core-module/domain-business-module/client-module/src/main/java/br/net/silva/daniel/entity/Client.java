package br.net.silva.daniel.entity;

import br.net.silva.daniel.dto.AddressDTO;
import br.net.silva.daniel.dto.ClientDTO;
import br.net.silva.daniel.interfaces.AggregateRoot;
import br.net.silva.daniel.interfaces.IFactory;
import br.net.silva.daniel.validation.Validation;
import br.net.silva.daniel.value_object.Address;

import java.util.UUID;

public class Client extends Validation implements AggregateRoot, IFactory<ClientDTO> {
    private final String id;
    private final String cpf;
    private String name;
    private String telephone;
    private boolean active;
    private Address address;

    public Client(String id, String cpf, String name, String telephone, boolean active, Address address) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.active = active;
        validate();
    }
    public Client(String cpf, String name, String telephone, Address address) {
        this(UUID.randomUUID().toString(), cpf, name, telephone, true, address);
    }

    public void deactivate() {
        this.active = false;
    }

    public void activate() {
        this.active = true;
    }

    public void editName(String name) {
        validateIfAllowToEdit();
        this.name = name;
        validate();
    }

    public void editTelephone(String telephone) {
        validateIfAllowToEdit();
        this.telephone = telephone;
        validate();
    }

    public void registerAddress(Address address) {
        validateIfAllowToEdit();
        this.address = address;
        validate();
    }

    public void validateIfAllowToEdit() {
        if (!active) {
            throw new IllegalArgumentException("Client is not active");
        }
    }

    @Override
    public void validate() {
        validateAttributeNotNullAndNotEmpty(cpf, "CPF is required");
        validateAttributeNotNullAndNotEmpty(name, "Name is required");
        validateAttributeNotNullAndNotEmpty(telephone, "Telephone is required");
        validateAttributeNonNull(address, "Address is required");
        address.validate();
    }

    @Override
    public ClientDTO create() {
        var addressDTO = new AddressDTO(address.street(), address.number(), address.complement(), address.neighborhood(), address.state(), address.city(), address.zipCode());
        return new ClientDTO(id, cpf, name, telephone, active, addressDTO);
    }
}
