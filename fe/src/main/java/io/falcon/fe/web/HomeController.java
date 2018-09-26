package io.falcon.fe.web;

import io.falcon.fe.model.Score;
import io.falcon.fe.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import javax.validation.Valid;

/**
 * WebSocket Controller - Receives Scorers from the FE and submits to Kafka broker for persistance/further handling
 *
 */
@Controller
@Slf4j
public class HomeController {

    @Autowired
    private ProducerService producerService;

    @MessageMapping("/scorer")
    public void updateScore(@Valid Score score) {
        log.info("Received message... sending to Kafka topic...");
        this.producerService.send(score);
    }
}