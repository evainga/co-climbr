package de.coclimbr;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
public class ClimberSearch {

    LocalDateTime date;
    Location location;
    Climber initialisingClimber;
    ClimberLevel level;
    List<Climber> joiningClimber;
}
