package br.net.silva.daniel.interfaces;

@FunctionalInterface
public interface IGenericBuilder<R, P> {
    R createFrom(P param);
}
