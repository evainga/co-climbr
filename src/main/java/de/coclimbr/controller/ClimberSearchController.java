package de.coclimbr.controller;

import de.coclimbr.service.ClimberSearch;
import de.coclimbr.service.ClimberSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ClimberSearchController {

    private final ClimberSearchService climberSearchService;

    @GetMapping(path = "/searches")
    public Flux<ClimberSearch> showAllClimberSearches() {
        return climberSearchService.getAllSearches();
    }

    @PostMapping("/searches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClimberSearch> createEntry(@RequestBody ClimberSearch climberSearch) {
        return climberSearchService.createSearch(climberSearch);
    }
}
