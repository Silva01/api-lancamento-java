package br.net.silva.daniel.interfaces;

import java.math.BigDecimal;

public interface IAccountParam extends Input, ICpfParam, IAgencyParam {
    Integer accountNumber();
    BigDecimal balance();
    String password();
    boolean active();
}
