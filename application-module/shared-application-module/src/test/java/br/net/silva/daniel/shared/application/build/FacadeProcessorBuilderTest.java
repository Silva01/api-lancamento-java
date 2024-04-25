package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.commons.DummyUseCase;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FacadeProcessorBuilderTest {

    @Test
    void createQueueThatUseCase_WithBuilders_ReturnsQueue() throws Exception {
        var useCase1 = UseCaseBuilder.makeTo(null, null, DummyUseCase.class);
        var useCase2 = UseCaseBuilder.makeTo(null, null, DummyUseCase.class);

        var sut = ((Builder<?>) FacadeBuilder
                .make()
                .withBuilderUseCases(useCase1, useCase2))
                .build();

        assertNotNull(sut);
        assertTrue(sut instanceof GenericFacadeDelegate);
    }

}