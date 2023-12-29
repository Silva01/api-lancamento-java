package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;

import java.util.List;
import java.util.Queue;

public abstract class AbstractFacade<P extends IGenericPort, U extends UseCase<P, IProcessResponse<?>>> {

    private final Queue<U> useCases;
    private final List<IValidations<P>> validationsList;

    protected AbstractFacade(Queue<U> useCases, List<IValidations<P>> validationsList) {
        this.useCases = useCases;
        this.validationsList = validationsList;
    }

    public IProcessResponse<?> exec(P param) throws GenericException {
        validate(param);
        return execProcess(param);
    }

    private void validate(P param) throws GenericException {
        for (IValidations<P> validations : validationsList) {
            validations.validate(param);
        }
    }

    @SuppressWarnings("unchecked")
    private IProcessResponse<?> execProcess(P param) throws GenericException {
        //TODO: Revisar se precisa de refatoracao
        for (U useCase : useCases) {
            param = (P) useCase.exec(param).build();
        }

        return (IProcessResponse<?>) param;
    }
}
