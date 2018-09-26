package io.falcon.persister.service;

import io.falcon.persister.config.KafkaConfigurationProperties;
import io.falcon.persister.model.Score;
import io.falcon.persister.persistence.ScoreRepository;
import io.falcon.persister.util.CustomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @since 26.09.2018
 * Persister Service - Store the payload and publish it to be viewed
 *
 */
@Service
@Slf4j
public class DefaultPersistenceService implements PersistenceService {
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private KafkaTemplate<String, Score> sender;

    @Autowired
    private CustomUtil customUtil;

    @Autowired
    private KafkaConfigurationProperties kafkaConfigurationProperties;

    @Override
    public Score save(Score score) {
        return this.scoreRepository.save(score);
    }

    @Override
    public void handlePayload(String payload) {
        Score score = this.customUtil.marshallPayloadToScore(payload);
        if(score != null) {
            if (score.getId() == null) {
                score = this.save(score);
            }
            sender.send(kafkaConfigurationProperties.getViewerTopic(), score);
        }
    }
}
