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
public class RabbitConfiguration {

    @Value("${spring.rabbitmq.queue-name:transaction-queue}")
    public String queueName;

    @Value("${spring.rabbitmq.exchange-name:transaction-exchange}")
    public String exchangeName;

    @Bean
    public Queue configureQueue() {
        return new Queue(queueName, false);
    }

    @Bean
    public Exchange configureExchange() {
        return ExchangeBuilder
                .directExchange(exchangeName)
                .build();
    }

    @Bean
    public Binding configureBinding(Queue queue, Exchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(queueName)
                .noargs();
    }
}
