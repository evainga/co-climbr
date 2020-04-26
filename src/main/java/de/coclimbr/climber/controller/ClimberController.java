package de.coclimbr.climber.controller;

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

import de.coclimbr.climber.data.Climber;
import de.coclimbr.climber.service.ClimberService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class ClimberController {

    private final ClimberService climberService;

    @GetMapping(path = "/climbers", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Flux<Climber> showAllClimbers() {
        return climberService.getAllClimbers();
    }

    @GetMapping(path = "/climbers/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_STREAM_JSON_VALUE })
    @ResponseStatus(HttpStatus.OK)
    public Mono<Climber> getClimber(@PathVariable String id) {
        return climberService.getClimber(id);
    }

    @DeleteMapping(path = "/climbers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteClimber(@PathVariable String id) {
        return climberService.deleteClimber(id);
    }

    @PostMapping("/climbers/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Climber> updateClimber(@PathVariable String id, @Valid @RequestBody Climber climber) {
        return climberService.updateClimber(id, climber);
    }

    @PostMapping("/climbers")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Climber> createClimber(@Valid @RequestBody Climber climber) {
        return climberService.createClimber(climber);
    }
}
