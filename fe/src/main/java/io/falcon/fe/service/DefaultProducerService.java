package io.falcon.fe.service;

import io.falcon.fe.config.KafkaConfigurationProperties;
import io.falcon.fe.model.Score;
import io.falcon.fe.util.SocketSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * @since 24.09.2018
 * Kafka Message Producer - Sends Scores, submitted via WebSocket
 *
 */
@Service
@Slf4j
@EnableConfigurationProperties(KafkaConfigurationProperties.class)
public class DefaultProducerService implements ProducerService {

    @Autowired
    private KafkaConfigurationProperties kafkaConfigurationProperties;

    @Autowired
    private KafkaTemplate<String, Score> sender;

    @Autowired
    private SocketSender socketSender;

    @Autowired
    private MessagePublisher redisCachePublisher;

    @Override
    public void send(Score score) {
        this.sender.send(kafkaConfigurationProperties.getPersisterTopic(), score);
    }

    @Override
    public CountDownLatch getLatch() {
        return this.socketSender.getLatch();
    }

    @KafkaListener(topics = "#{kafkaConfigurationProperties.getViewerTopic()}", groupId = "#{kafkaConfigurationProperties.getGroupId()}")
    public void listen(String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received payload:{} for topic: {}",payload, topic);
        socketSender.send(kafkaConfigurationProperties.getTopics(), payload);
        redisCachePublisher.publish(payload);
    }
}
