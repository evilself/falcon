package io.falcon.rest;

import io.falcon.rest.config.RestProducerConfig;
import io.falcon.rest.model.Score;
import io.falcon.rest.persistence.ScoreRepository;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @since 26.09.2018
 * REST endpoints test
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ContextConfiguration(classes = { RestApplication.class, RestProducerConfig.class, RestApplicationTests.TestConfig.class})
public class RestApplicationTests {
    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, "ToBeViewed", "ToBePersisted");

    private redis.embedded.RedisServer redisServer;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                                      .build();
        this.redisServer = new redis.embedded.RedisServer(6379);
        this.redisServer.start();
        scoreRepository.deleteAll();
    }

    @After
    public void cleanup() {
        this.redisServer.stop();
    }

    @Test
    public void contextLoads() { }

    @Test
    public void webAppContextIsValid() {
        ServletContext servletContext = wac.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("restApiController"));
    }

    @Test
    public void getAllScores() throws Exception {
        scoreRepository.save(new Score(Score.Team.ARSENAL, "Henry", 12));
        this.mockMvc.perform(get("/scores"))
                    .andDo(print())
                    .andExpect(status().is(HttpStatus.OK.value()))
                    .andExpect(jsonPath("$[0].team", is("ARSENAL")))
                    .andExpect(jsonPath("$[0].scorer", is("Henry")))
                    .andExpect(jsonPath("$[0].minute", is(12)));
    }

    @Test
    public void postScore() throws Exception {
        this.mockMvc.perform(post("/scores").contentType(MediaType.APPLICATION_JSON_VALUE)
                                            .content("{\"team\":\"MANCHESTERUTD\",\"scorer\":\"Keane\",\"minute\":\"78\"}\u0000")
                                            .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andDo(print())
                    .andReturn();

        assert this.scoreRepository.findAll()
                                   .size() > 0;
        assert this.scoreRepository.findAll()
                                   .get(0)
                                   .getScorer()
                                   .equalsIgnoreCase("Keane");
    }

    @TestConfiguration
    static class TestConfig {

        @Bean
        public ProducerFactory<String, Score> producerFactory() {
            Map<String, Object> configProps = new HashMap<>();
            configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, embeddedKafka.getBrokersAsString());
            configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
            configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
            return new DefaultKafkaProducerFactory<>(configProps);
        }

        @Bean
        public KafkaTemplate<String, Score> kafkaTemplate() {
            return new KafkaTemplate<>(producerFactory());
        }
    }


}
