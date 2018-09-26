package io.falcon.rest.persistence;

import io.falcon.rest.model.Score;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @since 25.09.2018
 *
 */
public interface ScoreRepository extends MongoRepository<Score, String> {
}