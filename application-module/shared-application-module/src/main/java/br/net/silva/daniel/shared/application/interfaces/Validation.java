package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Optional;

public interface Validation<T> {

    void validate(Optional<T> opt) throws GenericException;
}
