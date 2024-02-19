package br.net.silva.daniel.interfaces;

import java.math.BigDecimal;

public interface IAccountParam extends Input, ICpfParam, IAgencyParam, IAccountNumberParam {
    BigDecimal balance();
    String password();
    boolean active();
}
