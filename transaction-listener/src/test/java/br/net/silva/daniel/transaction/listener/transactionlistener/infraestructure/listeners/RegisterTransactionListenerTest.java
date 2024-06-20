package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.listeners;

import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.service.TransactionRegisterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.context.SpringRabbitTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RabbitListenerTest
@SpringRabbitTest
@ExtendWith({MockitoExtension.class})
class RegisterTransactionListenerTest {

    @Mock
    private TransactionRegisterService service;

    private RegisterTransactionListener listener;

    @BeforeEach
    void setUp() {
        listener = new RegisterTransactionListener(service, new ObjectMapper());
    }

    @Test
    void executerListener_WithValidData_ExecuteWithSuccess() {
        final var message = """
                            {
                              "sourceAccount": {
                                "accountNumber": 123456,
                                "agency": 1234,
                                "cpf": "12345678900"
                              },
                              "destinyAccount": {
                                "accountNumber": 654321,
                                "agency": 4321,
                                "cpf": "98765432100"
                              },
                              "batchTransaction": [
                                {
                                  "id": 1,
                                  "description": "Transaction 1",
                                  "price": 100.50,
                                  "quantity": 2,
                                  "type": "DEBIT",
                                  "idempotencyId": 101,
                                  "creditCardNumber": "4111111111111111",
                                  "creditCardCvv": 123
                                },
                                {
                                  "id": 2,
                                  "description": "Transaction 2",
                                  "price": 200.75,
                                  "quantity": 1,
                                  "type": "CREDIT",
                                  "idempotencyId": 102,
                                  "creditCardNumber": "4111111111111112",
                                  "creditCardCvv": 321
                                }
                              ]
                            }
                            """;

        listener.executeProcess(message);

        verify(service, times(1)).registerTransaction(any(BatchTransactionInput.class));
    }
}