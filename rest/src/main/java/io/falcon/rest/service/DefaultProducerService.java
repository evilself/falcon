package io.falcon.rest.service;

import io.falcon.rest.config.KafkaConfigurationProperties;
import io.falcon.rest.model.Score;
import io.falcon.rest.persistence.ScoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @since 25.09.2018
 * Kafka Message Producer - Sends Scores, submitted via REST Api
 *
 */
@Service
@Slf4j
public class DefaultProducerService implements ProducerService {

    @Autowired
    private KafkaConfigurationProperties configurationProperties;

    @Autowired
    private KafkaTemplate<String, Score> sender;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private MessagePublisher redisCachePublisher;

    @Override
    public Score save(Score score) {
        redisCachePublisher.publish(score.toString());
        return this.scoreRepository.save(score);

    }

    @Override
    public void send(Score score) {
        this.sender.send(configurationProperties.getPersisterTopic(), score);
    }

    @Override
    public List<Score> findAll() {
        return this.scoreRepository.findAll();
    }
}
