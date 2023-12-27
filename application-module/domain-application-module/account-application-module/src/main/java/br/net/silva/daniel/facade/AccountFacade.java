package br.net.silva.daniel.facade;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.interfaces.*;

import java.util.List;
import java.util.Queue;

public class AccountFacade extends AbstractFacade {
    protected AccountFacade(Queue<? extends UseCase<?, IProcessResponse<?>>> useCases, List<IValidations<? extends IGenericPort>> validationsList) {
        super(useCases, validationsList);
    }

    public IProcessResponse<?> execute(IGenericPort port) throws GenericException {
        return exec(port);
    }
}
