package io.falcon.fe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.fe.model.Score;
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
 * @since 24.09.2018
 * Kafka Message Producer - Sends Scores, submitted via WebSocket
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

    @Autowired
    private SimpMessagingTemplate socketTemplate;

    @Override
    public void send(Score score) {
        this.sender.send(persisterTopic, score);
    }

    @KafkaListener(topics = "toBeViewed", groupId = "group2")
    public void listen(String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        System.out.println("Received persisted score in FE:" + payload + " topic:"+topic);
        Score score = null;
        try {
            score = objectMapper.readValue(payload, Score.class);
        }
        catch (IOException e) {
            System.out.println("Exception occured in ");
            e.printStackTrace();

        }
        if(score != null) {
            socketTemplate.convertAndSend("/topic/scorers", score);
        }
    }
}
