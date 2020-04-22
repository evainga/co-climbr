package de.coclimbr.data;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import de.coclimbr.service.ClimberSearch;

public interface ClimberSearchRepository extends ReactiveMongoRepository<ClimberSearch, String> {
}
