package io.falcon.fe.util;

import com.google.common.base.Strings;
import io.falcon.fe.model.Score;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @since 26.09.2018
 * SocketSender - sending messages to subscribed WebSocket clients
 *
 */
@Component
public class SocketSender {
    @Getter
    private CountDownLatch latch = new CountDownLatch(1);

    @Autowired
    private SimpMessagingTemplate socketTemplate;

    @Autowired
    private CustomUtil customUtil;

    public void send(String topic, String payload) {
        if(Strings.isNullOrEmpty(topic)) {
            throw new RuntimeException("Topic should not be null!");
        }

        Score score = this.customUtil.marshallPayloadToScore(payload);
        if(score != null) {
            socketTemplate.convertAndSend(topic, score);
            latch.countDown();
        }
    }
}
