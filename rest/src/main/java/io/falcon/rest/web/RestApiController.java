package io.falcon.rest.web;

import io.falcon.rest.model.Score;
import io.falcon.rest.persistence.ScoreRepository;
import io.falcon.rest.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RestApiController {

    @Autowired
    private ProducerService producerService;

//    @Autowired
//    private MessageRepository messageRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Autowired
//    private SimpMessagingTemplate socketTemplate;


    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Score message(Score input) throws Exception {
        // this.redisTemplate.opsForHash().put("message", input, input);
        System.out.println("Mongo start");
        //this.messageMongoRepository.save(input);
        System.out.println("Mongo end");

        System.out.println("Kafka send start");
//        kafkaTemplate.send("Messages", input.getMessage());
//        //kafkaTemplate.send("Messages2", input.getMessage());
//        System.out.println("Kafka send start");
//
//        this.messageRepository.save(input);
//        System.out.println("done");
//        Thread.sleep(1000); // simulated delay
//        return new Score(HtmlUtils.htmlEscape(input.getMessage()));
        return null;
    }

    @GetMapping(value = "/scores", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Score> getAll() {
        return this.scoreRepository.findAll();
    }

    @PostMapping(value = "/scores", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Score saveScore(@RequestBody @Valid Score score) {
        Score saved = this.scoreRepository.save(score);
        //Throw it for the Kafka Listeners
        this.producerService.send(saved);
        //socketTemplate.convertAndSend("/topic/messages", message1);
        return saved;
    }


}
