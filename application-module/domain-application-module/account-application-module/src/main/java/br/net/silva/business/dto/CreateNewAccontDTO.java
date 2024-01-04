package br.net.silva.business.dto;

import br.net.silva.daniel.interfaces.ICreateAccountPord;

public class CreateNewAccontDTO implements ICreateAccountPord {

    private String cpf;
    private Integer agency;
    private String password;
    private boolean isWithCreditCard;
    @Override
    public Integer bankAgencyNumber() {
        return agency;
    }

    @Override
    public String cpf() {
        return cpf;
    }

    @Override
    public String password() {
        return password;
    }

    public boolean isWithCreditCard() {
        return isWithCreditCard;
    }
}
