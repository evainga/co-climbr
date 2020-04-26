package de.coclimbr.climber.service;

import org.springframework.stereotype.Component;

import de.coclimbr.climber.data.Climber;
import de.coclimbr.climber.data.ClimberRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ClimberService {

    private final ClimberRepository climberRepository;

    public Mono<Climber> createClimber(Climber climber) {
        return climberRepository.save(climber);
    }

    public Mono<Climber> getClimber(String id) {
        return climberRepository.findById(id);
    }

    public Mono<Climber> updateClimber(String id, Climber climber) {
        Mono<Climber> foundClimber = getClimber(id);
        if (foundClimber.equals(Mono.empty())) {
            return climberRepository.save(climber);
        } else {
            return foundClimber.flatMap(search -> updateClimber(climber, search));
        }
    }

    public Flux<Climber> getAllClimbers() {
        return climberRepository.findAll();
    }

    public Mono<Void> deleteClimber(String id) {
        return climberRepository.deleteById(id);
    }

    private Mono<Climber> updateClimber(Climber updatedClimber, Climber climber) {
        climber.setName(updatedClimber.getName());
        climber.setLevel(updatedClimber.getLevel());
        return climberRepository.save(climber);
    }
}
