package de.coclimbr.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import de.coclimbr.Climber;

public interface ClimberRepository extends ReactiveMongoRepository<Climber, String> {
}
