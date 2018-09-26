package io.falcon.persister;

import io.falcon.persister.model.Score;
import io.falcon.persister.persistence.ScoreRepository;
import io.falcon.persister.service.PersistenceService;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @since 26.09.2018
 * Test if microservice loads the Spring Context successfully and PersistanceService works as expected
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PersisterApplicationTests {
    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, "toBeViewed", "toBePersisted");

    @Autowired
    private PersistenceService persistenceService;

    @Autowired
    private ScoreRepository scoreRepository;

	@Test
	public void contextLoads() { }

	@Test
    public void testStorage() {
	    Score toBeSaved = new Score(Score.Team.ARSENAL, "Henry", 12);
	    Score stored = this.persistenceService.save(toBeSaved);
	    assert this.scoreRepository.findById(stored.getId()) != null;
    }

}
