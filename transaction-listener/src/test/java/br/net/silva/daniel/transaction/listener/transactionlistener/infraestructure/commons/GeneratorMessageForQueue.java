package br.net.silva.daniel.transaction.listener.transactionlistener.infraestructure.commons;

import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public final class GeneratorMessageForQueue {

    private final Connection connection;
    private final String queueName;

    public GeneratorMessageForQueue(Connection connection, String queueName) {
        this.connection = connection;
        this.queueName = queueName;
    }

    public void sendMessage(String message) throws IOException, TimeoutException {
        final var channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicPublish("", queueName, null, message.getBytes());
        channel.close();
    }
}
