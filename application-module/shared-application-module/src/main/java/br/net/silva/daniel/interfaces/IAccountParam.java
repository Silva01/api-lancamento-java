package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.interfaces.Input;

import java.math.BigDecimal;

public interface IAccountParam extends Input {
    Integer accountNumber();
    Integer agency();
    BigDecimal balance();
    String password();
    boolean active();
    String cpf();
}
