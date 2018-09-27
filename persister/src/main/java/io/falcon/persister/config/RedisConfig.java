package io.falcon.persister.config;

import io.falcon.persister.consumers.RedisSubscriber;
import io.falcon.persister.persistence.RedisScoreRepository;
import io.falcon.persister.util.CustomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * @since 27.09.2018
 * General Redis Config
 *
 */
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${redis.score.channel}")
    private String redisChannel;

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(redisChannel);
    }
//
    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    @Autowired
    public MessageListenerAdapter messageListener(CustomUtil customUtil, RedisScoreRepository redisScoreRepository) {
        return new MessageListenerAdapter(new RedisSubscriber(redisScoreRepository, customUtil));
    }

    @Bean
    @Autowired
    public RedisMessageListenerContainer redisContainer(CustomUtil customUtil, RedisScoreRepository redisScoreRepository) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(customUtil, redisScoreRepository), topic());
        return container;
    }
}
