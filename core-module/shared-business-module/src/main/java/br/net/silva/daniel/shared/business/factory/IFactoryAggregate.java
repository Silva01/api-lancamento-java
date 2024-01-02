package br.net.silva.daniel.shared.business.factory;

public interface IFactoryAggregate<R, T> {
    R create(T t);
}
