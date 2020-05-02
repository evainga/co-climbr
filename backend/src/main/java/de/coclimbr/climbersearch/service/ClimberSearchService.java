package de.coclimbr.climbersearch.service;

import org.springframework.stereotype.Component;

import de.coclimbr.climbersearch.data.ClimberSearch;
import de.coclimbr.climbersearch.data.ClimberSearchRepository;

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

    public Mono<ClimberSearch> getSearch(String id) {
        return climberSearchRepository.findById(id);
    }

    public Mono<ClimberSearch> updateSearch(String id, ClimberSearch climberSearch) {
        Mono<ClimberSearch> foundSearch = getSearch(id);
        if (foundSearch.equals(Mono.empty())) {
            return climberSearchRepository.save(climberSearch);
        } else {
            return foundSearch.flatMap(search -> updateSearch(climberSearch, search));
        }
    }

    public Flux<ClimberSearch> getAllSearches() {
        return climberSearchRepository.findAll();
    }

    public Mono<Void> deleteSearch(String id) {
        return climberSearchRepository.deleteById(id);
    }

    private Mono<ClimberSearch> updateSearch(ClimberSearch climberSearch, ClimberSearch search) {
        search.setDate(climberSearch.getDate());
        search.setLocation(climberSearch.getLocation());
        search.setLevel(climberSearch.getLevel());
        search.setJoiningClimberIds(climberSearch.getJoiningClimberIds());
        return climberSearchRepository.save(search);
    }
}
