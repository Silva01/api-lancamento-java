package br.net.silva.daniel.shared.application.decorator;

import br.net.silva.daniel.shared.business.exception.GenericException;

@FunctionalInterface
public interface ValidationDecorator <T> {
    void decorator(T t) throws GenericException;
}
