package io.falcon.rest.web;

import io.falcon.rest.model.Score;
import io.falcon.rest.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RestApiController {

    @Autowired
    private ProducerService producerService;

    @CrossOrigin("http://localhost:8080")
    @GetMapping(value = "/scores", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Score> getAll() {
        return this.producerService.findAll();
    }

    @CrossOrigin("http://localhost:8080")
    @PostMapping(value = "/scores", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Score saveScore(@RequestBody @Valid Score score) {
        Score saved = this.producerService.save(score);
        this.producerService.send(saved);
        return saved;
    }
}
