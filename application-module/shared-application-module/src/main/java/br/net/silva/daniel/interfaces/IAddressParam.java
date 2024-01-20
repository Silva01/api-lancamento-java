package br.net.silva.daniel.interfaces;

public interface IAddressParam extends Input {
    String street();
    String number();
    String complement();
    String neighborhood();
    String state();
    String city();
    String zipCode();
}
