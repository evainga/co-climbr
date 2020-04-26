package de.coclimbr.climbersearch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import de.coclimbr.climber.data.ClimberLevel;
import de.coclimbr.climbersearch.controller.ClimberSearchController;
import de.coclimbr.climbersearch.data.ClimberSearch;
import de.coclimbr.climbersearch.data.Location;
import de.coclimbr.climbersearch.service.ClimberSearchService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ClimberSearchControllerTest {

    private static final ClimberSearch CLIMBER_SEARCH = new ClimberSearch("123", LocalDateTime.now(), Location.BERTABLOCK, ClimberLevel.ADVANCED, null);
    private static final String ID = "123";

    private final ClimberSearchService climberSearchService = mock(ClimberSearchService.class);
    private final ClimberSearchController climberSearchController = new ClimberSearchController(climberSearchService);

    @Test
    void testGetAllSearchesWhenWithoutSearch() {
        //Given
        when(climberSearchService.getAllSearches()).thenReturn(Flux.just());

        //When
        Flux<ClimberSearch> climberSearchFlux = climberSearchController.showAllClimberSearches();

        //Then
        StepVerifier.create(climberSearchFlux)
                .verifyComplete();
    }

    @Test
    void testGetAllSearchesWhenHasSearch() {
        //Given
        when(climberSearchService.getAllSearches()).thenReturn(Flux.just(CLIMBER_SEARCH));

        //When
        Flux<ClimberSearch> climberSearchFlux = climberSearchController.showAllClimberSearches();

        //Then
        StepVerifier.create(climberSearchFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }

    @Test
    void testGetSearch() {
        //Given
        when(climberSearchService.getSearch(ID)).thenReturn(Mono.just(CLIMBER_SEARCH));

        //When
        Mono<ClimberSearch> climberSearchFlux = climberSearchController.getClimberSearch(ID);

        //Then
        StepVerifier.create(climberSearchFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }

    @Test
    void testDeleteSearch() {
        //Given
        when(climberSearchService.deleteSearch(ID)).thenReturn(Mono.empty());

        //When
        Mono<Void> climberSearchFlux = climberSearchController.deleteClimberSearch(ID);

        //Then
        StepVerifier.create(climberSearchFlux)
                .verifyComplete();
    }

    @Test
    void testUpdateSearch() {
        //Given
        when(climberSearchService.updateSearch(ID, CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));

        //When
        Mono<ClimberSearch> climberSearchFlux = climberSearchController.updateClimberSearch(ID, CLIMBER_SEARCH);

        //Then
        StepVerifier.create(climberSearchFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }

    @Test
    void testCreateSearch() {
        //Given
        when(climberSearchService.createSearch(CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));

        //When
        Mono<ClimberSearch> climberSearchFlux = climberSearchController.createClimberSearch(CLIMBER_SEARCH);

        //Then
        StepVerifier.create(climberSearchFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }
}