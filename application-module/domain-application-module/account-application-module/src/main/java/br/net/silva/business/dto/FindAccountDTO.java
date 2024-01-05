package br.net.silva.business.dto;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public class FindAccountDTO implements IGenericPort {

    private String cpf;
    private Integer agency;
    private Integer accountNumber;
    private String password;

    public FindAccountDTO(String cpf, Integer agency, Integer accountNumber) {
        this.cpf = cpf;
        this.agency = agency;
        this.accountNumber = accountNumber;
    }

    public FindAccountDTO() {
        this("", 0, 0);
    }

    public String cpf() {
        return cpf;
    }

    public Integer agency() {
        return agency;
    }

    public Integer accountNumber() {
        return accountNumber;
    }

    public String password() {
        return password;
    }

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, FindAccountDTO.class, "Type of class is incompatible with FindAccountDTO");
    }

    @Override
    public Object get() {
        return this;
    }
}
