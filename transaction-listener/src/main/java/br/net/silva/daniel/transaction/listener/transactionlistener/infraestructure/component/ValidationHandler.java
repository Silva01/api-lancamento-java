package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component;

import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.IValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.aggregate.BaseAccountAggregate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public final class ValidationHandler {

    private final List<IValidation> validations;

    public void executeValidations(List<BaseAccountAggregate> configurators) throws GenericException {
        for (BaseAccountAggregate configurator : configurators) {
            executeValidations(configurator);
        }
    }

    public void executeValidations(BaseAccountAggregate configurator) throws GenericException {
        for (IValidation validation : validations) {
            if (validation.isExecute(configurator)) {
                try {
                    validation.executeValidation(configurator);
                } catch (GenericException e) {
                    throw new GenericException(String.format("Account %d and agency %d: %s",
                                                             configurator.accountInput().accountNumber(),
                                                             configurator.accountInput().agency(),
                                                             e.getMessage()));
                }
            }
        }
    }
}
