package br.net.silva.daniel.shared.business.mapper;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public class PersonDTO implements IGenericPort {
    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, PersonDTO.class, "Class is not PersonDTO");
    }

    @Override
    public Object get() {
        return this;
    }
}
