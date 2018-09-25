package io.falcon.persister.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.falcon.persister.model.Score;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ScoreDeserializer implements Deserializer {

    @Override
    public void configure(final Map map, final boolean b) {

    }

    @Override
    public Object deserialize(final String s, final byte[] bytes) {
        System.out.println("DESESES : "+ s);
        ObjectMapper mapper = new ObjectMapper();
        Score         score   = null;
        try {
            score = mapper.readValue(s, Score.class);
            return score;
        } catch (Exception e) {
            System.out.println(e);
            return s;
        }
    }

    @Override
    public void close() {

    }
}