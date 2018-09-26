package io.falcon.fe.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @since 26.09.2018
 * General Kafka Config
 *
 */
@Configuration
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
@Component
@Primary
public class KafkaConfigurationProperties {
    private String server;
    private String persisterTopic;
    private String viewerTopic;
    private String groupId;
    private boolean enableAutoCommit;
    private int autoCommitIntervalMs;
    private int sessionTimeoutMs;
    private String autoOffset;
    private int concurrency;
    private int pollTimeout;
    private String topics;
}