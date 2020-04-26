package de.coclimbr.climber.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(value = "id", allowGetters = true)
@Document
public class Climber {

    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private ClimberLevel level;

    public Climber(String name, ClimberLevel level) {
        this.name = name;
        this.level = level;
    }
}
