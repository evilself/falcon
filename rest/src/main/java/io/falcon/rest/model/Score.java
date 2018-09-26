package io.falcon.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

/**
 * @since 26.09.2018
 * Score representation in rest service
 *
 */
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
    @NotEmpty
    private String scorer;

    @NotNull
    @Positive
    private Integer minute;

    public Score(Team team, String scorer, int minute) {
        this.team = team;
        this.scorer = scorer;
        this.minute = minute;
    }

    public enum Team {
        @JsonProperty("ARSENAL") ARSENAL, @JsonProperty("MANCHESTERUTD") MANCHESTERUTD
    }
}