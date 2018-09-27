package io.falcon.persister.consumers;

import io.falcon.persister.model.Score;
import io.falcon.persister.persistence.RedisScoreRepository;
import io.falcon.persister.util.CustomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisSubscriber implements MessageListener {

    private final RedisScoreRepository redisRepository;
    private final CustomUtil customUtil;

    @Autowired
    public RedisSubscriber(RedisScoreRepository redisScoreRepository, CustomUtil customUtil) {
        this.redisRepository = redisScoreRepository;
        this.customUtil = customUtil;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        if(message != null && message.getBody() != null) {
            System.out.println(customUtil+ " CUSTOMUTIL");
            Score payload = customUtil.marshallPayloadToScore(new String(message.getBody()));
            log.info("Redis Message received: {}", payload);
            redisRepository.save(payload);
        }
    }
}
