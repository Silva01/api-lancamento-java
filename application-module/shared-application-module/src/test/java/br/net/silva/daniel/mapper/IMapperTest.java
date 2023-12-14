package br.net.silva.daniel.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

class IMapperTest {

    @Mock
    IMapper<String, Integer> mapperMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMap() {
        String expected = "test";
        Mockito.when(mapperMock.map(any())).thenReturn(expected);

        // Act
        String result = mapperMock.map(1);

        // Assert
        assertEquals(expected, result);
    }
}