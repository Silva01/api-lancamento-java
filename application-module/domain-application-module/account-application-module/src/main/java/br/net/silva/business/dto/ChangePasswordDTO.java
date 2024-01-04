package br.net.silva.business.dto;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public class ChangePasswordDTO implements IGenericPort {

    private String cpf;
    private Integer agency;
    private Integer account;
    private String password;
    private String oldPassword;

    public ChangePasswordDTO(String cpf, Integer agency, Integer account, String password, String oldPassword) {
        this.cpf = cpf;
        this.agency = agency;
        this.account = account;
        this.password = password;
        this.oldPassword = oldPassword;
    }

    public ChangePasswordDTO() {
        this(null, null, null, null, null);
    }

    public String cpf() {
        return cpf;
    }

    public Integer agency() {
        return agency;
    }

    public Integer account() {
        return account;
    }

    public String password() {
        return password;
    }

    public String oldPassword() {
        return oldPassword;
    }

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, ChangePasswordDTO.class, "Type of class is incompatible with ChangePasswordDTO");
    }

    @Override
    public ChangePasswordDTO get() {
        return this;
    }
}
