package br.net.silva.daniel.shared.business.interfaces;

public interface IGenericPort {
    void accept(Class<?> clazz);
    Object get();
}
