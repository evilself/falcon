package io.falcon.persister.service;

import io.falcon.persister.model.Score;

public interface PersistenceService {
    Score save(Score score);

    void handlePayload(String payload);
}
