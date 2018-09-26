package io.falcon.fe.config;

import io.falcon.fe.model.Score;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @since 25.09.2018
 * WebSocket Producer config - Payload is serialized as JSON
 *
 */
@Configuration
@EnableConfigurationProperties(KafkaConfigurationProperties.class)
public class WebSocketProducerConfig {

    @Autowired
    private KafkaConfigurationProperties configurationProperties;

    @Bean
    public ProducerFactory<String, Score> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, configurationProperties.getServer());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Score> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

