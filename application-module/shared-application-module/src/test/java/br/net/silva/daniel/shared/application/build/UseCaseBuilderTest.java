package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.commons.DummyDoubleParamUseCase;
import br.net.silva.daniel.commons.DummyNoParameterUseCase;
import br.net.silva.daniel.commons.DummyThreeParamUseCase;
import br.net.silva.daniel.commons.DummyUseCase;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.mapper.GenericResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UseCaseBuilderTest {

    @Mock
    private ApplicationBaseGateway applicationBaseGateway;

    @Mock
    private GenericResponseMapper mapper;

    @Test
    void createUseCase_WithValidParameters_ReturnsUseCase() throws Exception {
        var useCase = UseCaseBuilder.makeTo(applicationBaseGateway, mapper, DummyDoubleParamUseCase.class).build();
        assertThat(useCase).isNotNull();

        var useCase2 = UseCaseBuilder.makeTo(applicationBaseGateway, mapper, DummyUseCase.class).build();
        assertThat(useCase2).isNotNull();
    }

    @Test
    void createUseCase_WithInvalidParameters_ReturnsException() {
        // Error With Three parameters in the Use Case
        assertThatThrownBy(() -> UseCaseBuilder.makeTo(applicationBaseGateway, mapper, DummyThreeParamUseCase.class).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid constructor for use case");

        // Error With No parameters in the Use Case
        assertThatThrownBy(() -> UseCaseBuilder.makeTo(applicationBaseGateway, mapper, DummyNoParameterUseCase.class).build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid constructor for use case");
    }

}