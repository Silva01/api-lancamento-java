package br.net.silva.daniel.interfaces;

public interface IClientParam extends Input {
    String id();
    String cpf();
    String name();
    String telephone();
    boolean active();
    Integer agency();
    IAddressParam address();
}
