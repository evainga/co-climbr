package de.coclimbr;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.coclimbr.data.ClimberSearchRepository;
import de.coclimbr.service.ClimberSearch;
import de.coclimbr.service.ClimberSearchService;
import de.coclimbr.service.Location;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ClimberSearchServiceTest {
    private static final ClimberSearch CLIMBER_SEARCH = ClimberSearch.builder().date(LocalDateTime.now()).initialisingClimber(Climber.builder().build()).location(Location.BERTABLOCK).build();
    private final ClimberSearchRepository climberSearchRepository = mock(ClimberSearchRepository.class);

    @BeforeEach
    void init() {
        when(climberSearchRepository.save(CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));
    }

    @Test
    void createClimber() {
        //Given
        var service = new ClimberSearchService(climberSearchRepository);

        //When
        Mono<ClimberSearch> climberSearch = service.createSearch(CLIMBER_SEARCH);

        //Then
        StepVerifier.create(climberSearch)
                .assertNext(search -> assertThat(search.getLocation()).isEqualTo(Location.BERTABLOCK))
                .verifyComplete();
    }

    @Test
    void createEmptyClimberWhenNoInput() {
        //Given
        ClimberSearchRepository climberSearchRepository = mock(ClimberSearchRepository.class);
        var service = new ClimberSearchService(climberSearchRepository);

        //When
        Mono<ClimberSearch> climberSearch = service.createSearch(ClimberSearch.builder().build());

        //Then
        assertThat(climberSearch).isNull();
    }

    @Test
    void createEmptyClimberWhenOnlyOneInputIsMissing() {
        //Given
        ClimberSearchRepository climberSearchRepository = mock(ClimberSearchRepository.class);
        var service = new ClimberSearchService(climberSearchRepository);

        //When
        Mono<ClimberSearch> climberSearch = service.createSearch(ClimberSearch.builder().location(Location.BERTABLOCK).initialisingClimber(Climber.builder().build()).build());

        //Then
        assertThat(climberSearch).isNull();
    }


}