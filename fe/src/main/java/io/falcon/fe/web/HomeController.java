package io.falcon.fe.web;

import io.falcon.fe.model.Score;
import io.falcon.fe.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * WebSocket Controller
 *
 */
@Controller
@Slf4j
public class HomeController {

    @Autowired
    private ProducerService producerService;

    @MessageMapping("/scorer")
    @SendTo("/topic/scorers")
    public void updateScore(@Valid Score score) throws Exception {
        log.info("Sending to Kafka topic...");
        this.producerService.send(score);
    }
}