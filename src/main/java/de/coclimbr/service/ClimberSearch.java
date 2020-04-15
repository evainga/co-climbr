package de.coclimbr.service;

import de.coclimbr.Climber;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Value
@RequiredArgsConstructor
public class ClimberSearch {

    LocalDateTime date;
    Location location;
    Climber initialisingClimber;
    ClimberLevel level;
    List<Climber> joiningClimber;
}
