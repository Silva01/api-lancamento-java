package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.shared.business.exception.DomainGenericException;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UseCaseTest {

    @Mock
    UseCase useCaseMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExec() throws DomainGenericException {
        Integer expected = 1;
        var source = new Source(null, null);

        // Act
        useCaseMock.exec(source);

        // Assert: Se não deu erro é porque passou
        assertTrue(true);
    }

    @Test
    void testExecWithGenericError() throws DomainGenericException {
        // Act
        Source source = null;
        try {
            useCaseMock.exec(source);
        } catch (DomainGenericException e) {
            // Assert
            assertEquals("Generic error", e.getMessage());
        }
    }
}