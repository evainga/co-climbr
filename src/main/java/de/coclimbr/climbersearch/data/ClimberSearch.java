package de.coclimbr.climbersearch.data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import de.coclimbr.ClimberLevel;
import de.coclimbr.Location;

import lombok.Data;

@Data
@JsonIgnoreProperties(value = "id", allowGetters = true)
@Document
public class ClimberSearch {

    @Id
    private String id;

    @NonNull
    private String initialisingClimberId;
    @NonNull
    private LocalDateTime date;
    @NonNull
    private Location location;
    @NonNull
    private ClimberLevel level;

    private List<String> joiningClimberIds;

    public ClimberSearch(String initialisingClimberId, LocalDateTime date, Location location, ClimberLevel level, List<String> joiningClimberIds) {
        this.initialisingClimberId = initialisingClimberId;
        this.date = date;
        this.location = location;
        this.level = level;
        this.joiningClimberIds = joiningClimberIds;
    }
}
