package io.falcon.fe.service;

import io.falcon.fe.model.Score;

import java.util.concurrent.CountDownLatch;

/**
 * @since 24.09.2018
 *
 */
public interface ProducerService {
    void send(Score score);
    CountDownLatch getLatch();
}
