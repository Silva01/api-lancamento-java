package br.net.silva.daniel.interfaces;

public interface IProcessResponse<T extends IGenericPort> {
    T build();
}
