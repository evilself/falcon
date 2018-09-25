package io.falcon.persister.consumers;



import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.persister.model.Score;
import io.falcon.persister.persistence.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PersisterConsumer {

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private KafkaTemplate<String, Score> sender;

    @Autowired
    ObjectMapper objectMapper;

    @KafkaListener(topics = "ToBePersisted", groupId = "group1")
    public void listen(String payload, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        System.out.println("Received score in Persister:" + payload + " topic:"+topic);
        Score score = null;
        try {
            score = objectMapper.readValue(payload, Score.class);
        }
        catch (IOException e) {
            System.out.println("Error in Persister ");
            e.printStackTrace();

        }
        if(score != null) {
            if (score.getId() == null) {
                score = scoreRepository.save(score);
            }
            sender.send("toBeViewed", score);
        }
    }
}
