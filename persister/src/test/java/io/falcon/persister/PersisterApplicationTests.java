package io.falcon.persister;

import io.falcon.persister.model.Score;
import io.falcon.persister.persistence.RedisScoreRepository;
import io.falcon.persister.persistence.ScoreRepository;
import io.falcon.persister.service.PersistenceService;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * @since 26.09.2018
 * Test if microservice loads the Spring Context successfully and PersistanceService works as expected
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {PersisterApplication.class, PersisterApplicationTests.EmbededRedisTestConfiguration.class})
public class PersisterApplicationTests {
    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, "toBeViewed", "toBePersisted");

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private RedisScoreRepository redisScoreRepository;

	@Test
	public void contextLoads() { }

	@Test
    public void testStorage() {
	    Score toBeSaved = new Score(Score.Team.ARSENAL, "Henry", 12);
	    Score stored = this.persistenceService.save(toBeSaved);
	    assert this.scoreRepository.findById(stored.getId()) != null;
    }

    @Test
    public void testRedisStorage() {
        Score toBeSaved = new Score(Score.Team.ARSENAL, "Henry", 12);
        Score stored = this.redisScoreRepository.save(toBeSaved);
        assert this.scoreRepository.findById(stored.getId()) != null;
    }

    @TestConfiguration
    static class EmbededRedisTestConfiguration {

        private final redis.embedded.RedisServer redisServer;

        public EmbededRedisTestConfiguration(@Value("${spring.redis.port}") final int redisPort) throws IOException {
            this.redisServer = new redis.embedded.RedisServer(redisPort);
        }

        @PostConstruct
        public void startRedis() {
            this.redisServer.start();
        }

        @PreDestroy
        public void stopRedis() {
            this.redisServer.stop();
        }
    }

}
