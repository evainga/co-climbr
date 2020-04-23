package de.coclimbr.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import de.coclimbr.service.ClimberLevel;
import de.coclimbr.service.ClimberSearch;
import de.coclimbr.service.ClimberSearchService;
import de.coclimbr.service.Location;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ClimberSearchControllerTest {

    private static final ClimberSearch CLIMBER_SEARCH = new ClimberSearch("123", LocalDateTime.now(), Location.BERTABLOCK, ClimberLevel.ADVANCED, null);

    private final ClimberSearchService climberSearchService = mock(ClimberSearchService.class);
    private final ClimberSearchController climberSearchController = new ClimberSearchController(climberSearchService);

    @Test
    void testWithoutEntry() {
        //Given
        when(climberSearchService.getAllSearches()).thenReturn(Flux.just());

        //When
        Flux<ClimberSearch> climberSearchFlux = climberSearchController.showAllClimberSearches();

        //Then
        StepVerifier.create(climberSearchFlux)
                .verifyComplete();
    }

    @Test
    void testHasEntry() {
        //Given
        when(climberSearchService.getAllSearches()).thenReturn(Flux.just(CLIMBER_SEARCH));

        //When
        Flux<ClimberSearch> climberSearchFlux = climberSearchController.showAllClimberSearches();

        //Then
        StepVerifier.create(climberSearchFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }
}