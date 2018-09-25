package io.falcon.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.rest.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @since 25.09.2018
 * Kafka Message Producer - Sends Scores, submitted via REST Api
 *
 */
@Service
public class DefaultProducerService implements ProducerService {

    @Value("${kafka.persister.topic}")
    private String persisterTopic;

    @Autowired
    private KafkaTemplate<String, Score> sender;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void send(Score score) {
        this.sender.send(persisterTopic, score);
    }
}
