package io.falcon.fe.config;

import org.junit.ClassRule;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.test.rule.KafkaEmbedded;

@Configuration
public class FeTestConfig {

    @ClassRule
    public static KafkaEmbedded embeddedKafka =
            new KafkaEmbedded(1, true, "toBeViewed", "toBePersisted");
}
