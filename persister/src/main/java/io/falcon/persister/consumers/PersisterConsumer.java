package io.falcon.persister.consumers;

import io.falcon.persister.config.KafkaConfigurationProperties;
import io.falcon.persister.service.PersistenceService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PersisterConsumer {

    @Autowired
    private KafkaConfigurationProperties kafkaConfigurationProperties;

    @Autowired
    private PersistenceService persistenceService;

    @KafkaListener(topics = "#{kafkaConfigurationProperties.getPersisterTopic()}", groupId = "#{kafkaConfigurationProperties.getGroupId()}")
    public void listen(String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        log.info("Received payload:{} for topic: {}",payload, topic);
        this.persistenceService.handlePayload(payload);
    }
}
