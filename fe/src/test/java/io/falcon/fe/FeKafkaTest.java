package io.falcon.fe;

import io.falcon.fe.config.FeTestConfig;
import io.falcon.fe.config.WebsocketConsumerConfig;
import io.falcon.fe.config.WebsocketProducer;
import io.falcon.fe.model.Score;
import io.falcon.fe.service.ProducerService;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class FeKafkaTest {

    @ClassRule
    public static KafkaEmbedded embeddedKafka =
            new KafkaEmbedded(1, true, "toBeViewed", "toBePersisted");

    @Autowired
    private KafkaTemplate<String, Score> sender;

    @Autowired
    private ProducerService producerService;

    @Test
    public void testReceive() throws Exception {
        sender.send("toBeViewed", new Score(Score.Team.ARSENAL, "Henry", 1));

        producerService.getLatch().await(10000, TimeUnit.MILLISECONDS);
        assertThat(producerService.getLatch().getCount()).isEqualTo(0);
    }

}
