package de.coclimbr.service;

import de.coclimbr.Climber;
import de.coclimbr.data.ClimberSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ClimberSearchService {

    private final ClimberSearchRepository climberSearchRepository;

    public Mono<ClimberSearch> createSearch(ClimberSearch climberSearch) {
        LocalDateTime date = climberSearch.getDate();
        Location location = climberSearch.getLocation();
        Climber initialisingClimber = climberSearch.getInitialisingClimber();

        if (date == null || location == null || initialisingClimber == null) {
            return null;
        } else {
            return climberSearchRepository.save(climberSearch);
        }
    }

    public Flux<ClimberSearch> getAllSearches() {
        return climberSearchRepository.findAll();
    }
}
