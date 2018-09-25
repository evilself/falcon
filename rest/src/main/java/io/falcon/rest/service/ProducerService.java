package io.falcon.rest.service;

import io.falcon.rest.model.Score;

/**
 * @since 25.09.2018
 *
 */
public interface ProducerService {
    void send(Score score);
}
