package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.value_object.Source;

import java.util.List;
import java.util.Queue;

public class GenericFacadeDelegate<U extends UseCase> {

    private final Queue<U> useCases;
    private final List<? extends IValidations> validationsList;

    public GenericFacadeDelegate(Queue<U> useCases, List<? extends IValidations> validationsList) {
        this.useCases = useCases;
        this.validationsList = validationsList;
    }

    public void exec(Source input) throws GenericException {
        validate(input);
        execProcess(input);
    }

    private void validate(Source input) throws GenericException {
        for (IValidations validations : validationsList) {
            validations.validate(input);
        }
    }

    private void execProcess(Source input) throws GenericException {
        for (U useCase : useCases) {
            useCase.exec(input);
        }
    }
}
