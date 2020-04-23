package de.coclimbr.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.coclimbr.service.ClimberSearch;
import de.coclimbr.service.ClimberSearchService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ClimberSearchController {

    private final ClimberSearchService climberSearchService;

    @GetMapping(path = "/searches", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE })
    public Flux<ClimberSearch> showAllClimberSearches() {
        return climberSearchService.getAllSearches();
    }

    @PostMapping("/searches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClimberSearch> createEntry(@Valid @RequestBody ClimberSearch climberSearch) {
        return climberSearchService.createSearch(climberSearch);
    }
}
