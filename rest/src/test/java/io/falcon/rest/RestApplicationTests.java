package io.falcon.rest;

import io.falcon.rest.model.Score;
import io.falcon.rest.persistence.ScoreRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.activation.MimeType;
import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RestApplicationTests {

    @ClassRule
    public static KafkaEmbedded embeddedKafka =
            new KafkaEmbedded(1, true, "toBeViewed", "toBePersisted");

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

	@Test
	public void contextLoads() {
	}

	@Test
    public void getScores() {
	    Score score = new Score(Score.Team.ARSENAL, "Henry", 1);
        Score score2 = new Score(Score.Team.ARSENAL, "BERGKAMP", 1);
        this.scoreRepository.save(score);
        this.scoreRepository.save(score2);
    }

    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("restApiController"));
    }

    @Test
    public void givenHomePageURI_whenMockMVC_thenReturnsIndexJSPViewName() throws Exception {
        this.mockMvc.perform(get("/scores")).andDo(print())
                    .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    public void postMeth() throws Exception {
        this.mockMvc.perform(post("/scores").contentType(MediaType.APPLICATION_JSON_VALUE).content("{\"team\":\"MANCHESTERUTD\"," +
                                                                                                   "\"scorer\":\"Keane\"," +
                                                                           "\"minute\":\"78\"}\u0000").accept(MediaType.APPLICATION_JSON_VALUE)).andDo
                (print())
                    .andExpect(status().is(HttpStatus.OK.value()));

    }

}
