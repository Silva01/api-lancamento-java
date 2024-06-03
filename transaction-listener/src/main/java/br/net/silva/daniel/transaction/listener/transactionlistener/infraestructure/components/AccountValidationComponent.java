package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.components;

import br.net.silva.daniel.transaction.listener.transactionlistener.domain.validation.AccountValidation;
import br.net.silva.daniel.transaction.listener.transactionlistener.domain.validation.Validation;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.model.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public final class AccountValidationComponent {

    @Bean
    public Validation<Optional<Account>> accountValidation() {
        return new AccountValidation();
    }
}
