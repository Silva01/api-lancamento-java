package silva.daniel.project.app.domain.account.gateway;

import br.net.silva.business.value_object.input.ReversalTransactionInput;
import br.net.silva.daniel.shared.application.gateway.ApplicationBaseGateway;
import br.net.silva.daniel.shared.application.gateway.ParamGateway;
import br.net.silva.daniel.shared.application.utils.ConverterUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReversalTransactionGateway implements ApplicationBaseGateway<ReversalTransactionInput> {


    @Value("${spring.rabbitmq.queue-name:transaction-reversal-queue}")
    public String queueName;

    @Value("${spring.rabbitmq.exchange-name:transaction-reversal-exchange}")
    public String exchangeName;

    private final RabbitTemplate rabbitTemplate;

    public ReversalTransactionGateway(RabbitTemplate rabbitTemplate) {
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
    public Optional<ReversalTransactionInput> findById(ParamGateway param) {
        return Optional.empty();
    }

    @Override
    public List<ReversalTransactionInput> findAllBy(ParamGateway param) {
        return List.of();
    }

    @Override
    public ReversalTransactionInput save(ReversalTransactionInput entity) {
        rabbitTemplate.convertSendAndReceive(exchangeName, queueName, ConverterUtils.convertObjectToJson(entity));
        return entity;
    }

    @Override
    public void saveAll(List<ReversalTransactionInput> paramList) {

    }
}
