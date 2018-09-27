package io.falcon.persister.persistence;

import io.falcon.persister.model.Score;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @since 26.09.2018
 * MongoDB Repository
 *
 */
public interface ScoreRepository extends MongoRepository<Score, String> {
}