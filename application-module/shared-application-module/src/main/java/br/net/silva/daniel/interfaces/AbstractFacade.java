package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.SharedParamDelegate;
import br.net.silva.daniel.exception.GenericException;

import java.util.List;
import java.util.Queue;

public abstract class AbstractFacade<U extends UseCase<SharedParamDelegate, IProcessResponse>> {

    private final Queue<U> useCases;
    private final List<IValidations<SharedParamDelegate>> validationsList;

    protected AbstractFacade(Queue<U> useCases, List<IValidations<SharedParamDelegate>> validationsList) {
        this.useCases = useCases;
        this.validationsList = validationsList;
    }

    public IProcessResponse exec(SharedParamDelegate delegate) throws GenericException {
        validate(delegate);
        return execProcess(delegate);
    }

    private void validate(SharedParamDelegate delegate) throws GenericException {
        for (IValidations<SharedParamDelegate> validations : validationsList) {
            validations.validate(delegate);
        }
    }

    private IProcessResponse execProcess(SharedParamDelegate delegate) throws GenericException {
        for (U useCase : useCases) {
            var response = useCase.exec(delegate);
            delegate.addResponse(response);
        }

        return delegate;
    }
}
