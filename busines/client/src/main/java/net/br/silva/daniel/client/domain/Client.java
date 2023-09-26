package net.br.silva.daniel.client.domain;

import net.br.silva.daniel.client.domain.Address;

import java.util.Objects;

public class Client {
    private final Long id;
    private final String cpf;
    private String name;
    private String telephone;
    private Address address;
    private boolean isActive;

    public Client(Long id, String cpf, String name, String telephone, Address address) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.telephone = telephone;
        this.address = address;
        this.activateClient();
        this.validate();
    }

    public void desactivateClient() {
        this.isActive = false;
    }

    public void activateClient() {
        this.isActive = true;
    }

    public void editName(String name) {
        this.validateIfAllowToEdit();
        this.name = name;
    }

    public void editTelephone(String telephone) {
        this.validateIfAllowToEdit();
        this.telephone = telephone;
    }

    public void registerAddress(Address address) {
        this.validateIfAllowToEdit();
        this.address = address;
    }

    private void validate() {
        Objects.requireNonNull(this.name, "Name is not null");
        Objects.requireNonNull(this.telephone, "Telephone is not null");
        Objects.requireNonNull(this.cpf, "CPF is not null");

        if (this.name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name is not empty");
        }

        if (this.telephone.trim().isEmpty()) {
            throw new IllegalArgumentException("Telephone is not empty");
        }

        if (this.cpf.trim().isEmpty() || this.cpf.length() < 11) {
            throw new IllegalArgumentException("CPF is invalid");
        }
    }

    private void validateIfAllowToEdit() {
        if (!this.isActive) {
            throw new IllegalArgumentException("Client edit not allowed");
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", address=" + address +
                ", isActive=" + isActive +
                '}';
    }
}
