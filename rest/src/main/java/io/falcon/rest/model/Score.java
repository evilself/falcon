package io.falcon.rest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
        ARSENAL, MANCHESTERUTD
    }
}