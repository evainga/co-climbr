package de.coclimbr.data;

import de.coclimbr.Climber;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClimberRepository extends ReactiveCrudRepository<Climber, String> {
}
