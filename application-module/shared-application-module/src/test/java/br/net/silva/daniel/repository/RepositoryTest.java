package br.net.silva.daniel.repository;

import br.net.silva.daniel.shared.application.gateway.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class RepositoryTest {

    @Mock
    Repository<String> repositoryMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExec() {
        String expected = "test";
        Mockito.when(repositoryMock.exec(any())).thenReturn(expected);

        // Act
        String result = repositoryMock.exec("any argument");

        // Assert
        assertEquals(expected, result);
    }
}