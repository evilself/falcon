package io.falcon.fe.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "websocket")
@Getter
@Setter
@Component
@Primary
public class WebSocketConfigurationProperties {
    private String stompEndpoint;
    private String broker;
    private String appDestinationPrefix;
}
