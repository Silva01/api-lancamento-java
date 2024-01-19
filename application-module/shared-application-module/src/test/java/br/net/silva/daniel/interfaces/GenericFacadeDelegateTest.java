package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.exception.GenericException;
import br.net.silva.daniel.shared.business.interfaces.IGenericOutput;
import br.net.silva.daniel.value_object.Source;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class GenericFacadeDelegateTest {

    private GenericFacadeDelegate genericFacadeDelegate;

    @Mock
    private UseCase useCase;

    @Mock
    private IValidations stringNotNullValidation;

//    @BeforeEach
//    void setup() {
//        var basicInput = new BasicTest();
//        basicInput.setName("Teste com input");
//        // Next create a object that type is UseCase and return the processResponse
//        useCase = (p) -> {
//            p.map().put("Test", basicInput);
//        };
//
//        // Next create a object that type is IValidations and validate if the string is not null
//        stringNotNullValidation = (p) -> {
//            var basicTest = (BasicTest) p.map().get("Test");
//            if (basicTest.getName().isEmpty()) {
//                throw new GenericException("String must not be null");
//            }
//        };
//
//        // Next create a object that type is Queue and add the useCase
//        Queue<UseCase> useCases = new LinkedList<>();
//        useCases.add(useCase);
//
//        // Next create a object that type is List and add the stringNotNullValidation
//        List<IValidations> validationsList = List.of(stringNotNullValidation);
//
//        // Finally create a object that type is GenericFacadeDelegate and pass the useCases and validationsList
//        genericFacadeDelegate = new GenericFacadeDelegate(useCases, validationsList);
//
//    }
//    @Test
//    void mustExecuteFacadeWithSucess() throws GenericException {
//
//        var basicInput = new BasicTest();
//        basicInput.setName("Teste com input");
//
//        var source = new Source();
//        source.input().put("Test", "Teste com input");
//        source.map().put("Test", basicInput);
//
//        genericFacadeDelegate.exec(source);
//
//        assertTrue(source.map().containsKey("Test"));
//        assertNotNull(source.map().get("Test"));
//
//        var basicTest = (BasicTest) source.map().get("Test");
//
//        assertNotNull(basicTest);
//        assertEquals("Teste com input", basicTest.getName());
//    }
//
//    @Test
//    void mustExecuteFacadeWithException() throws GenericException {
//
//        var basicInput = new BasicTest();
//        basicInput.setName("");
//
//        var source = new Source();
//        source.input().put("Test", "");
//        source.map().put("Test", basicInput);
//
//        var exceptionResponse = Assertions.assertThrows(GenericException.class, () -> genericFacadeDelegate.exec(source));
//        assertEquals("String must not be null", exceptionResponse.getMessage());
//    }
//
//    @Test
//    void mustExecuteFacadeWithExceptionWhenPortIsNull() throws GenericException {
//
//        var source = new Source();
//        source.input().put("Test", null);
//
//        Assertions.assertThrows(NullPointerException.class, () -> genericFacadeDelegate.exec(source));
//    }
//
//    private class BasicTest implements IGenericOutput {
//        private String name;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//    }

}