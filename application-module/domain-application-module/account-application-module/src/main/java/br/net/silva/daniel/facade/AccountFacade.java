package br.net.silva.daniel.facade;

import br.net.silva.daniel.SharedParamDelegate;
import br.net.silva.daniel.interfaces.AbstractFacade;
import br.net.silva.daniel.interfaces.IProcessResponse;
import br.net.silva.daniel.interfaces.UseCase;

import java.util.Queue;

public class AccountFacade extends AbstractFacade {
    protected AccountFacade(Queue<? extends UseCase<?, IProcessResponse>> useCases) {
        super(useCases);
    }

    public IProcessResponse execute(SharedParamDelegate delegate) {
        return exec(delegate);
    }
}
