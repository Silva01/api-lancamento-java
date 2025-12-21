package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.DomainGenericException;

import java.util.Queue;

public class GenericFacadeDelegate {

    private final Queue<UseCase<?>> useCases;

    public GenericFacadeDelegate(Queue<UseCase<?>> useCases) {
        this.useCases = useCases;
    }

    public void exec(Source input) throws DomainGenericException {
        execProcess(input);
    }

    private void execProcess(Source input) throws DomainGenericException {
        for (UseCase<?> useCase : useCases) {
            useCase.exec(input);
        }
    }
}
