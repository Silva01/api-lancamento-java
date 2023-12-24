package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.SharedParamDelegate;
import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.exception.ProcessExecErrorException;

import java.util.Queue;

public abstract class AbstractFacade<U extends UseCase<SharedParamDelegate, IProcessResponse>> {

    private final Queue<U> useCases;

    protected AbstractFacade(Queue<U> useCases) {
        this.useCases = useCases;
    }

    public IProcessResponse exec(SharedParamDelegate delegate) {
        useCases.forEach(useCase -> {
            try {
                var response = useCase.exec(delegate);
                delegate.addResponse(response);
            } catch (GenericException e) {
                throw new ProcessExecErrorException(e);
            }
        });

        return delegate;
    }
}
