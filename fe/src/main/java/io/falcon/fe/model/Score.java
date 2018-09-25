package io.falcon.fe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @since 25.09.2018
 * Score - Keeps track of scorer, team and minute - our Payload
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
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