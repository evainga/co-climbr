package de.coclimbr;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClimberRepository extends ReactiveCrudRepository<Climber, String> {
}
