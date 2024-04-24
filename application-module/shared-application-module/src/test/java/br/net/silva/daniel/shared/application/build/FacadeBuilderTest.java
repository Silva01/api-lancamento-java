package br.net.silva.daniel.shared.application.build;

import br.net.silva.daniel.commons.DummyUseCase;
import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FacadeBuilderTest {

    @Test
    void createQueueThatUseCase_WithBuilders_ReturnsQueue() throws Exception {
        var useCase1 = UseCaseBuilder.make().prepareUseCaseFrom(DummyUseCase.class).withBaseRepository(null).withGenericMapper(null);
        var useCase2 = UseCaseBuilder.make().prepareUseCaseFrom(DummyUseCase.class).withBaseRepository(null).withGenericMapper(null);

        var sut = ((Builder<?>) FacadeBuilder
                .make()
                .withBuilderUseCases(useCase1, useCase2))
                .build();

        assertNotNull(sut);
        assertTrue(sut instanceof GenericFacadeDelegate);
    }

}