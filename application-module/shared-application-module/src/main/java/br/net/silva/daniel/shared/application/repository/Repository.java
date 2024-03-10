package br.net.silva.daniel.shared.application.repository;

public interface Repository<R> {
    R exec(Object... args);
}
