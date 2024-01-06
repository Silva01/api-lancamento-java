package br.net.silva.business.dto;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public class CreateNewAccountByCpfDTO implements IGenericPort {

    private String cpf;
    private Integer bankAgencyNumber;
    private String password;

    public CreateNewAccountByCpfDTO(String cpf, Integer bankAgencyNumber, String password) {
        this.cpf = cpf;
        this.bankAgencyNumber = bankAgencyNumber;
        this.password = password;
    }

    public CreateNewAccountByCpfDTO() {
        this(null, 0, null);
    }

    public String cpf() {
        return cpf;
    }

    public Integer agency() {
        return bankAgencyNumber;
    }

    public String password() {
        return password;
    }

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, CreateNewAccountByCpfDTO.class, "Type of class is not CreateNewAccountByCpfDTO");
    }

    @Override
    public Object get() {
        return this;
    }
}
