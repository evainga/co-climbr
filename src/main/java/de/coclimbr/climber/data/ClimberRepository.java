package de.coclimbr.climber.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClimberRepository extends ReactiveMongoRepository<Climber, String> {
}
