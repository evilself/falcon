package io.falcon.persister.persistance;

import io.falcon.persister.model.Score;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScoreRepository extends MongoRepository<Score, String> {
}