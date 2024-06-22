package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.listeners;

import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.service.TransactionRegisterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@AllArgsConstructor
@RabbitListener(queues = "${spring.rabbitmq.queue-name:transaction-queue}")
public class RegisterTransactionListener {

    private final TransactionRegisterService service;
    private final ObjectMapper mapper;

    @RabbitHandler
    public void executeProcess(String message) {
        final var batchTransaction = convertToObject(message);
        service.registerTransaction(batchTransaction);
    }

    private BatchTransactionInput convertToObject(String message) {
        try {
            return mapper.readValue(message, BatchTransactionInput.class);
        } catch (Exception e) {
            throw new RuntimeException("Error converting message to object", e);
        }
    }
}
