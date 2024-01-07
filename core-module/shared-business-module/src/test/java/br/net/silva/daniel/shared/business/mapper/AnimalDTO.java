package br.net.silva.daniel.shared.business.mapper;

import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;

public class AnimalDTO implements IGenericPort {
    private String name;
    private Integer age;

    private boolean isAnimal = true;

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

    public boolean isAnimal() {
        return isAnimal;
    }

    @Override
    public void accept(Class<?> clazz) {
        ValidateUtils.isTypeOf(clazz, AnimalDTO.class, "Class is not AnimalDTO");
    }

    @Override
    public Object get() {
        return this;
    }
}