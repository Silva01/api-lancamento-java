package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.component;

import br.net.silva.daniel.shared.business.exception.GenericException;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.interfaces.IValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.transaction.value_object.ValidatorConfigurator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public final class ValidationHandler {

    private final List<IValidation> validations;

    public void executeValidations(List<ValidatorConfigurator> configurators) throws GenericException {
        for (ValidatorConfigurator configurator : configurators) {
            executeValidations(configurator);
        }
    }

    public void executeValidations(ValidatorConfigurator configurator) throws GenericException {
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
