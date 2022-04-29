package world.inetum.bigg.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import world.inetum.bigg.exception.IngestorException;
import world.inetum.bigg.model.KafkaMessage;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
public class IngestorService {

    private final Logger log = LogManager.getLogger();
    private final long sendTimeout;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public IngestorService(
            @Value("${ingestor.kafka.send.timeout:#{10}}") long sendTimeout,
            KafkaTemplate<String, Object> kafkaTemplate
    ) {
        this.sendTimeout = sendTimeout;
        this.kafkaTemplate = kafkaTemplate;
    }

    public SendResult<String, Object> sendMessageAndHandleErrors(KafkaMessage message) {
        try {
            return sendMessage(message);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            throw new IngestorException(String.format(
                    "Could not ingest message to topic %s with key %s:%n%n%s",
                    message.getTopic(), message.getKey(), message.getData().toString()
            ), e);
        }
    }

    private SendResult<String, Object> sendMessage(KafkaMessage message)
            throws InterruptedException, ExecutionException, TimeoutException {
        SendResult<String, Object> result = kafkaTemplate
                .send(message.getTopic(), message.getKey(), message.getData())
                .get(sendTimeout, TimeUnit.SECONDS);
        logMessageIngested(message);
        return result;
    }

    private void logMessageIngested(KafkaMessage message) {
        log.info("ingested data to topic {} with key {}: \n{}",
                message.getKey(),
                message.getTopic(),
                message.getData()
        );
    }
}
