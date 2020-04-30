package de.coclimbr.climbersearch.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import de.coclimbr.climber.service.ClimberService;
import de.coclimbr.climbersearch.data.ClimberSearch;
import de.coclimbr.climbersearch.service.ClimberSearchService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ClimberSearchController {

    private static final ResponseStatusException CLIMBER_ID_NOT_FOUND = new ResponseStatusException(HttpStatus.NOT_FOUND, "ClimberId not found");

    private final ClimberSearchService climberSearchService;
    private final ClimberService climberService;

    @GetMapping(path = "/searches", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Flux<ClimberSearch> showAllClimberSearches() {
        return climberSearchService.getAllSearches();
    }

    @GetMapping(path = "/searches/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClimberSearch> getClimberSearch(@PathVariable String id) {
        return climberSearchService.getSearch(id);
    }

    @DeleteMapping(path = "/searches/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteClimberSearch(@PathVariable String id) {
        return climberSearchService.deleteSearch(id);
    }

    @PostMapping("/searches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClimberSearch> createClimberSearch(@Valid @RequestBody ClimberSearch climberSearch) {
        return climberService.getClimber(climberSearch.getInitialisingClimberId())
                .switchIfEmpty(Mono.error(CLIMBER_ID_NOT_FOUND))
                .flatMap(c -> climberSearchService.createSearch(climberSearch));
    }

    @PostMapping("/searches/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ClimberSearch> updateClimberSearch(@PathVariable String id, @Valid @RequestBody ClimberSearch climberSearch) {
        return climberService.getClimber(climberSearch.getInitialisingClimberId())
                .switchIfEmpty(Mono.error(CLIMBER_ID_NOT_FOUND))
                .flatMap(c -> climberSearchService.updateSearch(id, climberSearch));
    }

}
