package br.net.silva.daniel.interfaces;

import br.net.silva.daniel.shared.application.interfaces.GenericFacadeDelegate;
import br.net.silva.daniel.shared.application.interfaces.Input;
import br.net.silva.daniel.shared.application.interfaces.Output;
import br.net.silva.daniel.shared.application.interfaces.UseCase;
import br.net.silva.daniel.shared.application.value_object.Source;
import br.net.silva.daniel.shared.business.exception.GenericException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.LinkedList;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GenericFacadeDelegateTest {

    private GenericFacadeDelegate genericFacadeDelegate;

    @Mock
    private UseCase<String> useCase;

    @BeforeEach
    void setup() {
        var basicInput = new BasicTest();
        basicInput.setName("Teste com input");
        // Next create a object that type is UseCase and return the processResponse
        useCase = (p) -> {
            ((BasicTest) p.output()).setName("Test List");
            return "return test";
        };

        // Next create a object that type is Queue and add the useCase
        Queue<UseCase<?>> useCases = new LinkedList<>();
        useCases.add(useCase);

        // Finally create a object that type is GenericFacadeDelegate and pass the useCases and validationsList
        genericFacadeDelegate = new GenericFacadeDelegate<>(useCases);

    }
    @Test
    void mustExecuteFacadeWithSucess() throws GenericException {

        var basicInput = new BasicTest();
        basicInput.setName("Teste com input");

        var source = new Source(new BasicTest(), basicInput);

        genericFacadeDelegate.exec(source);

        var basicTest = (BasicTest) source.output();

        assertNotNull(basicTest);
        assertEquals("Test List", basicTest.getName());
    }

    private class BasicTest implements Input, Output {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}