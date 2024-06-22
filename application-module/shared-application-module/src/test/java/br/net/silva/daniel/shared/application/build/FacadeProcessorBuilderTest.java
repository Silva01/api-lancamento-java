package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.commons.DummyUseCase;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FacadeProcessorBuilderTest {

    @Test
    void createQueueThatUseCase_WithBuilders_ReturnsQueue() throws Exception {
        var useCase1 = UseCaseBuilder.makeTo(null, null, DummyUseCase.class);
        var useCase2 = UseCaseBuilder.makeTo(null, null, DummyUseCase.class);

        var sut = FacadeBuilder
                .make()
                .withBuilderUseCase(useCase1)
                .andWithBuilderUseCase(useCase2)
                .build();

        assertThat(sut).isNotNull();
    }

}