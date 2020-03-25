package de.coclimbr;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClimberService {

    public ClimberSearch createSearch(LocalDateTime date, Climber climber, Location location) {
        if (date == null || climber == null || location == null) {
            return null;
        } else
            return ClimberSearch.builder().date(date).location(location).initialisingClimber(climber).build();
    }
}
