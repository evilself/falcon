package io.falcon.rest.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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