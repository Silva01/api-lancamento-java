package br.net.silva.daniel.shared.application.interfaces;

import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;

import java.util.Queue;

public class GenericFacadeDelegate<U extends UseCase> {

    private final Queue<U> useCases;

    public GenericFacadeDelegate(Queue<U> useCases) {
        this.useCases = useCases;
    }

    public void exec(Source input) throws GenericException {
        execProcess(input);
    }

    private void execProcess(Source input) throws GenericException {
        for (U useCase : useCases) {
            useCase.exec(input);
        }
    }
}
