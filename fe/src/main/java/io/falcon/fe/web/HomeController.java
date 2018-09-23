package io.falcon.fe.web;

import io.falcon.fe.model.Score;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

/**
 * WebSocket Controller
 *
 */
@Controller
public class HomeController {

    @MessageMapping("/scorer")
    @SendTo("/topic/scorers")
    public Score updateScore(Score score) throws Exception {
        return score;
    }
}