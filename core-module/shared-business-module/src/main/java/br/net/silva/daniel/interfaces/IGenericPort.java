package br.net.silva.daniel.interfaces;

public interface IGenericPort {
    void accept(Class<?> clazz);
    Object get();
}
