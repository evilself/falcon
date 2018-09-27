package io.falcon.persister.persistence;

import io.falcon.persister.model.Score;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisScoreRepository extends CrudRepository<Score, String> { }
