package br.net.silva.daniel.shared.application.decorator;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;

@FunctionalInterface
public interface ValidationDecorator <T> {
    void decorator(T t) throws DomainGenericException;
}
