package br.net.silva.business.build;

import br.net.silva.business.value_object.output.AccountOutput;
import br.net.silva.daniel.shared.application.gateway.FindApplicationBaseGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CreditCardNumberExistsValidationBuilderTest {

    @Mock
    private FindApplicationBaseGateway<AccountOutput> findAccountGateway;

    @Test
    void createValidation_WithBuilder_ReturnsNewValidation() throws Exception {
        var validation = new CreditCardNumberExistsValidationBuilder()
                .withRepository(findAccountGateway)
                .build();

        assertThat(validation).isNotNull();
    }
}