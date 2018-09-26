package io.falcon.persister.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

import io.falcon.persister.exceptions.PayloadException;
import io.falcon.persister.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @since 26.09.2018
 * Utility class - helps with marshalling/unamrshalling of Payloads
 *
 */
@Component
public class CustomUtil {

    @Autowired
    private ObjectMapper objectMapper;

    public Score marshallPayloadToScore(String payload) {
        if(Strings.isNullOrEmpty(payload)) throw new PayloadException("Payload is null");

        Score score;
        try {
            score = objectMapper.readValue(payload, Score.class);
        }
        catch (IOException e) {
            throw new PayloadException("Could not marshall payload into Score");
        }
        return score;
    }
}
