package br.net.silva.daniel.interfaces;

import java.math.BigDecimal;

public interface IAccountPord {
    Integer number();
    Integer bankAgencyNumber();
    BigDecimal balance();
    String password();
    String cpf();
}
