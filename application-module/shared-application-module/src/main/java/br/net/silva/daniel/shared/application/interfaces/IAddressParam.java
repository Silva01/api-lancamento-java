package br.net.silva.daniel.shared.application.interfaces;

public interface IAddressParam extends Input {
    String street();
    String number();
    String complement();
    String neighborhood();
    String state();
    String city();
    String zipCode();
}
