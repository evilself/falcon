package io.falcon.fe.service;

import io.falcon.fe.model.Score;

/**
 * @since 27.09.2018
 * Message Publisher
 *
 */
public interface MessagePublisher {
    void publish(String score);
}
