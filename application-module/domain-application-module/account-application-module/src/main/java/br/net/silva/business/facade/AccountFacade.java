package br.net.silva.business.facade;

import br.net.silva.daniel.interfaces.AbstractFacade;
import br.net.silva.daniel.interfaces.IValidations;
import br.net.silva.daniel.interfaces.UseCase;

import java.util.List;
import java.util.Queue;

public class AccountFacade extends AbstractFacade {
    protected AccountFacade(Queue<? extends UseCase<?>> useCases, List<IValidations> validationsList) {
        super(useCases, validationsList);
    }
}
