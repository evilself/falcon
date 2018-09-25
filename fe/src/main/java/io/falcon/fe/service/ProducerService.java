package io.falcon.fe.service;

import io.falcon.fe.model.Score;

/**
 * @since 24.09.2018
 *
 */
public interface ProducerService {
    void send(Score score);
}
