package io.falcon.rest.service;

import io.falcon.rest.model.Score;

import java.util.List;

/**
 * @since 25.09.2018
 *
 */
public interface ProducerService {
    Score save(Score score);
    void send(Score score);
    List<Score> findAll();
}
