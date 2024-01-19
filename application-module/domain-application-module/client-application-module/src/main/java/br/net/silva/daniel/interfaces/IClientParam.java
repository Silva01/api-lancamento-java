package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.value_object.input.AddressRequestDTO;

public interface IClientParam extends Input {
    String id();
    String cpf();
    String name();
    String telephone();
    boolean active();
    AddressRequestDTO address();
}
