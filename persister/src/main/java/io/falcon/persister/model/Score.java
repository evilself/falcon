package io.falcon.persister.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * @since 26.09.2018
 * Score model - Persister representation
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Document
@ToString
public class Score implements Serializable {
    @Id
    private String id;

    @NotNull
    private Team team;

    @NotNull
    @NotEmpty
    private String scorer;

    @NotNull
    @Positive
    private int minute;

    public Score(Team team, String scorer, int minute) {
        this.team = team;
        this.scorer = scorer;
        this.minute = minute;
    }

    public enum Team {
        @JsonProperty("ARSENAL") ARSENAL, @JsonProperty("MANCHESTERUTD") MANCHESTERUTD
    }
}