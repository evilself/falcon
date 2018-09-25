package io.falcon.persister.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

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