package de.coclimbr.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.lang.NonNull;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Builder
@Value
@RequiredArgsConstructor
public class ClimberSearch {

    @Id
    long id;

    @NonNull
    Long initialisingClimberId;
    @NonNull
    LocalDateTime date;
    @NonNull
    Location location;
    @NonNull
    ClimberLevel level;

    List<Long> joiningClimberIds;
}
