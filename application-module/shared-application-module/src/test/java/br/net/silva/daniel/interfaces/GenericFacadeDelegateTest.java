package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.shared.business.interfaces.IGenericPort;
import br.net.silva.daniel.shared.business.interfaces.IProcessResponse;
import br.net.silva.daniel.shared.business.utils.ValidateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GenericFacadeDelegateTest {

    private GenericFacadeDelegate genericFacadeDelegate;

    @Mock
    private UseCase useCase;

    @Mock
    private IValidations stringNotNullValidation;

    @BeforeEach
    void setup() {

        // Create a object that type is IGenericPort
        IGenericPort port = new IGenericPort() {
            @Override
            public void accept(Class<?> clazz) {
                ValidateUtils.isTypeOf(clazz, String.class, "Port must be a String");
            }

            @Override
            public Object get() {
                return "Test";
            }
        };

        // Next create a object that type is IProcessResponse and return the port
        IProcessResponse processResponse = () -> port;

        // Next create a object that type is UseCase and return the processResponse
        useCase = (p) -> processResponse;

        // Next create a object that type is IValidations and validate if the string is not null
        stringNotNullValidation = (p) -> {
            var string = (String) p.get();
            if (string.isEmpty()) {
                throw new GenericException("String must not be null");
            }
        };

        // Next create a object that type is Queue and add the useCase
        Queue<UseCase> useCases = new LinkedList<>();
        useCases.add(useCase);

        // Next create a object that type is List and add the stringNotNullValidation
        List<IValidations> validationsList = List.of(stringNotNullValidation);

        // Finally create a object that type is GenericFacadeDelegate and pass the useCases and validationsList
        genericFacadeDelegate = new GenericFacadeDelegate(useCases, validationsList);

    }
    @Test
    void mustExecuteFacadeWithSucess() throws GenericException {

        IGenericPort port = new IGenericPort() {
            @Override
            public void accept(Class<?> clazz) {
                ValidateUtils.isTypeOf(clazz, String.class, "Port must be a String");
            }

            @Override
            public Object get() {
                return "Test";
            }
        };

        var response = genericFacadeDelegate.exec(port);

        assertNotNull(response);

        var genericPortResponse = response.build();

        assertNotNull(genericPortResponse);

        var stringResponse = (String) genericPortResponse.get();

        assertNotNull(stringResponse);
        assertEquals("Test", stringResponse);
    }

    @Test
    void mustExecuteFacadeWithException() throws GenericException {

        IGenericPort port = new IGenericPort() {
            @Override
            public void accept(Class<?> clazz) {
                ValidateUtils.isTypeOf(clazz, String.class, "Port must be a String");
            }

            @Override
            public Object get() {
                return "";
            }
        };

        var exceptionResponse = Assertions.assertThrows(GenericException.class, () -> genericFacadeDelegate.exec(port));
        assertEquals("String must not be null", exceptionResponse.getMessage());
    }

    @Test
    void mustExecuteFacadeWithExceptionWhenPortIsNull() throws GenericException {

        IGenericPort port = new IGenericPort() {
            @Override
            public void accept(Class<?> clazz) {
                ValidateUtils.isTypeOf(clazz, String.class, "Port must be a String");
            }

            @Override
            public Object get() {
                return null;
            }
        };

        var exceptionResponse = Assertions.assertThrows(NullPointerException.class, () -> genericFacadeDelegate.exec(port));
    }

}