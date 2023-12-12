package br.net.silva.daniel.repository;

public interface Repository<R> {
    R exec(Object... args);
}
