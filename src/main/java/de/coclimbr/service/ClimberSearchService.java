package de.coclimbr.service;

import org.springframework.stereotype.Component;

import de.coclimbr.data.ClimberSearchRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClimberSearchService {

    private final ClimberSearchRepository climberSearchRepository;

    public Mono<ClimberSearch> createSearch(ClimberSearch climberSearch) {
        return climberSearchRepository.save(climberSearch);
    }

    public Flux<ClimberSearch> getAllSearches() {
        return climberSearchRepository.findAll();
    }
}
