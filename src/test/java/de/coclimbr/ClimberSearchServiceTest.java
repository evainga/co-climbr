package de.coclimbr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import de.coclimbr.data.ClimberSearchRepository;
import de.coclimbr.service.ClimberLevel;
import de.coclimbr.service.ClimberSearch;
import de.coclimbr.service.ClimberSearchService;
import de.coclimbr.service.Location;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ClimberSearchServiceTest {

    private static final ClimberSearch CLIMBER_SEARCH = new ClimberSearch("123", LocalDateTime.now(), Location.BERTABLOCK, ClimberLevel.ADVANCED, null);
    private final ClimberSearchRepository climberSearchRepository = mock(ClimberSearchRepository.class);

    @Test
    void createClimberSearch() {
        //Given
        when(climberSearchRepository.save(CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));
        var service = new ClimberSearchService(climberSearchRepository);

        //When
        Mono<ClimberSearch> climberSearch = service.createSearch(CLIMBER_SEARCH);

        //Then
        StepVerifier.create(climberSearch)
                .assertNext(search -> assertThat(search.getLocation()).isEqualTo(Location.BERTABLOCK))
                .verifyComplete();
    }

    @Test
    void getAllSearches() {
        //Given
        when(climberSearchRepository.findAll()).thenReturn(Flux.just(CLIMBER_SEARCH, CLIMBER_SEARCH));
        var service = new ClimberSearchService(climberSearchRepository);

        //When
        Flux<ClimberSearch> climberSearches = service.getAllSearches();

        //Then
        StepVerifier.create(climberSearches)
                .assertNext(search -> assertThat(search.getLocation()).isEqualTo(Location.BERTABLOCK))
                .assertNext(search -> assertThat(search.getLocation()).isEqualTo(Location.BERTABLOCK))
                .verifyComplete();
    }
}