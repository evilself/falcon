package io.falcon.persister.service;

import io.falcon.persister.model.Score;

/**
 * @since 26.09.2018
 * Handles storage of message and returning it to the Message Queue
 *
 */
public interface PersistenceService {
    Score save(Score score);
    void handlePayload(String payload);
}
