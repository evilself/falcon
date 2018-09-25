package io.falcon.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Score implements Serializable {
    private String id;

    @NotNull
    private Team team;

    @NotNull
    private String scorer;

    @NotNull
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