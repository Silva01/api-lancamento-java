package br.net.silva.daniel.factory;

public interface IFactoryAggregate<R, T> {
    R create(T t);
}
