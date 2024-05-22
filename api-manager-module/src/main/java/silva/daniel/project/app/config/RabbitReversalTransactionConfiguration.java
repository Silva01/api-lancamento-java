package silva.daniel.project.app.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitReversalTransactionConfiguration {

    @Value("${spring.rabbitmq.queue-name:transaction-reversal-queue}")
    public String queueName;

    @Value("${spring.rabbitmq.exchange-name:transaction-reversal-exchange}")
    public String exchangeName;

    @Bean
    public Queue configureReversalQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Exchange configureReversalExchange() {
        return ExchangeBuilder
                .directExchange(exchangeName)
                .build();
    }

    @Bean
    public Binding configureReversalBinding() {
        return BindingBuilder
                .bind(configureReversalQueue())
                .to(configureReversalExchange())
                .with(queueName)
                .noargs();
    }
}
