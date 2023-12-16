package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;

class UseCaseTest {

    @Mock
    UseCase<String, Integer> useCaseMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExec() throws GenericException {
        Integer expected = 1;
        Mockito.when(useCaseMock.exec(any())).thenReturn(expected);

        // Act
        Integer result = useCaseMock.exec("any argument");

        // Assert
        Assert.assertEquals(expected, result);
    }

    @Test
    void testExecWithGenericError() throws GenericException {
        Mockito.when(useCaseMock.exec(any())).thenThrow(new GenericException("Generic error"));

        // Act
        try {
            useCaseMock.exec("any argument");
        } catch (GenericException e) {
            // Assert
            Assert.assertEquals("Generic error", e.getMessage());
        }
    }
}