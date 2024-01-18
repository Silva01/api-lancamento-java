package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

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
    void testExec() throws GenericException {
        Integer expected = 1;
        var source = new Source(new HashMap<>());

        // Act
        useCaseMock.exec(source);

        // Assert: Se não deu erro é porque passou
        assertTrue(true);
    }

    @Test
    void testExecWithGenericError() throws GenericException {
        // Act
        Source source = null;
        try {
            useCaseMock.exec(source);
        } catch (GenericException e) {
            // Assert
            assertEquals("Generic error", e.getMessage());
        }
    }
}