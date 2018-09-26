package io.falcon.fe;

import io.falcon.fe.config.FeTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = FeTestConfig.class)
public class FeApplicationTests {
	@Test
	public void contextLoads() { }
}
