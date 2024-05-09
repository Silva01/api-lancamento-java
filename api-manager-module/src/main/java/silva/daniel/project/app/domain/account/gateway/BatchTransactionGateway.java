package silva.daniel.project.app.domain.account.gateway;

import br.net.silva.business.value_object.input.BatchTransactionInput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.utils.ConverterUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BatchTransactionGateway implements ApplicationBaseGateway<BatchTransactionInput> {


    @Value("${spring.rabbitmq.queue-name:transaction-queue}")
    public String queueName;

    @Value("${spring.rabbitmq.exchange-name:transaction-exchange}")
    public String exchangeName;

    private final RabbitTemplate rabbitTemplate;

    public BatchTransactionGateway(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public boolean deleteById(ParamGateway param) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public Optional<BatchTransactionInput> findById(ParamGateway param) {
        return Optional.empty();
    }

    @Override
    public List<BatchTransactionInput> findAllBy(ParamGateway param) {
        return List.of();
    }

    @Override
    public BatchTransactionInput save(BatchTransactionInput entity) {
        rabbitTemplate.convertSendAndReceive(exchangeName, queueName, ConverterUtils.convertObjectToJson(entity));
        return entity;
    }

    @Override
    public void saveAll(List<BatchTransactionInput> paramList) {

    }
}
