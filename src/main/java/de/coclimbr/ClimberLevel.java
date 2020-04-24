package de.coclimbr;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public enum ClimberLevel {
    ADVANCED,
    MEDIUM,
    BEGINNER;

    public static interface ClimberRepository extends ReactiveMongoRepository<Climber, String> {
    }
}
