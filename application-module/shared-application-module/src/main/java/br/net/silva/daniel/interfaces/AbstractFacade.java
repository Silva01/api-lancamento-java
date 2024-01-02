package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;

import java.util.List;
import java.util.Queue;

public abstract class AbstractFacade<P extends IGenericPort, U extends UseCase<IProcessResponse<?>>> {

    private final Queue<U> useCases;
    private final List<? extends IValidations> validationsList;

    protected AbstractFacade(Queue<U> useCases, List<? extends IValidations> validationsList) {
        this.useCases = useCases;
        this.validationsList = validationsList;
    }

    public IProcessResponse<?> exec(P param) throws GenericException {
        validate(param);
        return execProcess(param);
    }

    private void validate(P param) throws GenericException {
        for (IValidations validations : validationsList) {
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
