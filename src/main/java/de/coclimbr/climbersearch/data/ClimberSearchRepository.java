package de.coclimbr.climbersearch.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClimberSearchRepository extends ReactiveMongoRepository<ClimberSearch, String> {
}
