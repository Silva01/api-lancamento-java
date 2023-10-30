package br.net.silva.daniel.entity;

import br.net.silva.daniel.interfaces.AggregateRoot;
import br.net.silva.daniel.validation.Validation;
import br.net.silva.daniel.value_object.Address;

import java.util.UUID;

public class Client extends Validation implements AggregateRoot {
    private String id;
    private final String cpf;
    private final String name;
    private final String telephone;
    private boolean active;
    private final Address address;

    public Client(String cpf, String name, String telephone, Address address) {
        this.id = UUID.randomUUID().toString();
        this.cpf = cpf;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.active = true;
    }

    @Override
    protected void validate() {
        validateAttributeNotNullAndNotEmpty(cpf, "CPF is required");
        validateAttributeNotNullAndNotEmpty(name, "Name is required");
        validateAttributeNotNullAndNotEmpty(telephone, "Telephone is required");
        validateAttributeNonNull(address, "Address is required");
        address.validate();
    }
}
