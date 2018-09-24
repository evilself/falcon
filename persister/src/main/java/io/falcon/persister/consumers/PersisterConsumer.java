package io.falcon.persister.consumers;

import io.falcon.persister.model.Score;
import io.falcon.persister.persistance.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
public class PersisterConsumer {

    @Autowired
    private ScoreRepository scoreRepository;

    @KafkaListener(topics = "Messages", groupId = "group1")
    public void listen(Score score, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        System.out.println("ARSENAL ARSENAL: first" + score + " topic:"+topic);
        if(score != null) {
            scoreRepository.save(score);
        }
    }
}
