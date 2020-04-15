package de.coclimbr.data;

import de.coclimbr.service.ClimberSearch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ClimberSearchRepository extends ReactiveCrudRepository<ClimberSearch, String> {
}
