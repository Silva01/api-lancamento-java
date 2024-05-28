package silva.daniel.project.app.integration.web;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import silva.daniel.project.app.commons.IntegrationAssertCommons;
import silva.daniel.project.app.commons.MysqlTestContainer;
import silva.daniel.project.app.commons.RabbitMQTestContainer;
import silva.daniel.project.app.commons.RequestIntegrationCommons;
import silva.daniel.project.app.domain.account.request.RefundRequest;
import silva.daniel.project.app.domain.client.FailureResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("e2e")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/delete_client.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
@Sql(scripts = {"/sql/import_client.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(errorMode = SqlConfig.ErrorMode.CONTINUE_ON_ERROR))
class ReversalTransactionControllerIT extends MysqlTestContainer implements IntegrationAssertCommons, RabbitMQTestContainer {

    private static final String API_REFUND_TRANSACTION = "/api/transaction/refund";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RequestIntegrationCommons requestCommons;

    private Channel channel;

    private static final String QUEUE_NAME = "transaction-reversal-queue";

    @BeforeEach
    void setUp() throws IOException, TimeoutException {
        final var factory = new ConnectionFactory();

        factory.setHost(RABBIT_MQ_CONTAINER.getHost());
        factory.setPort(RABBIT_MQ_CONTAINER.getAmqpPort());
        factory.setUsername(RABBIT_MQ_CONTAINER.getAdminUsername());
        factory.setPassword(RABBIT_MQ_CONTAINER.getAdminPassword());

        final var connection = factory.newConnection();
        this.channel = connection.createChannel();
    }

    @AfterEach
    void tearDown() throws IOException, TimeoutException {
        channel.queuePurge(QUEUE_NAME);
        channel.close();
    }

    @Test
    void refundTransaction_WithValidData_ReturnsStatus200() throws IOException {
        final var request = new RefundRequest("12345678901", 1L, 2222L);
        final var result = restTemplate.postForEntity(API_REFUND_TRANSACTION, request, Void.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isEqualTo(1);

        GetResponse response = channel.basicGet(QUEUE_NAME, true);
        assertThat(response).isNotNull();

        final var sut = new String(response.getBody(), StandardCharsets.UTF_8);
        assertThat(sut).contains(request.cpf());
        assertThat(sut).contains(request.transactionId().toString());
        assertThat(sut).contains(request.idempotencyId().toString());
    }

    @ParameterizedTest
    @MethodSource("silva.daniel.project.app.commons.TransactionRequestBuilder#provideInvalidDataForRefundTransaction")
    void refundTransaction_WithInvalidData_ReturnsStatus406(RefundRequest request) throws IOException {
        requestCommons.assertPostRequest(API_REFUND_TRANSACTION, request, FailureResponse.class, this::assertInvalidData);
        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }

    @Test
    void refundTransaction_WithClientNotExists_ReturnsStatus404() throws IOException {
        final var request = new RefundRequest("12345678900", 1L, 2222L);
        requestCommons.assertPostRequest(API_REFUND_TRANSACTION, request, FailureResponse.class, this::assertClientNotExists);
        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }

    @Test
    void refundTransaction_WithClientDeactivated_ReturnsStatus409() throws IOException {
        final var request = new RefundRequest("12345678903", 1L, 2222L);
        requestCommons.assertPostRequest(API_REFUND_TRANSACTION, request, FailureResponse.class, this::assertClientDeactivatedExists);
        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }

    @Test
    void refundTransaction_WithAccountNotExists_ReturnsStatus404() throws IOException {
        final var request = new RefundRequest("12345678988", 2L, 2222L);
        requestCommons.assertPostRequest(API_REFUND_TRANSACTION, request, FailureResponse.class, this::assertAccountNotExists);
        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }

    @Test
    void refundTransaction_WithAccountDeactivated_ReturnsStatus409() throws IOException {
        final var request = new RefundRequest("12345678904", 2L, 2222L);
        requestCommons.assertPostRequest(API_REFUND_TRANSACTION, request, FailureResponse.class, this::assertAccountAlreadyDeactivated);
        AMQP.Queue.DeclareOk queueDeclare = channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        assertThat(queueDeclare.getMessageCount()).isZero();
    }
}