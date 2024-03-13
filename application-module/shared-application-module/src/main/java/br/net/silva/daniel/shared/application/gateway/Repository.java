package br.net.silva.daniel.shared.application.gateway;

public interface Repository<R> {
    R exec(Object... args);
}
