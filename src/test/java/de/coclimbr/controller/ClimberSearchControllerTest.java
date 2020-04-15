package de.coclimbr.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.coclimbr.service.ClimberLevel;
import de.coclimbr.service.ClimberSearch;
import de.coclimbr.service.ClimberSearchService;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class ClimberSearchControllerTest {

    private final ClimberSearchService climberSearchService = mock(ClimberSearchService.class);
    private final ClimberSearchController climberSearchController = new ClimberSearchController(climberSearchService);

    @BeforeEach
    void init() {
        when(climberSearchService.getAllSearches()).thenReturn(Flux.just(ClimberSearch.builder().level(ClimberLevel.ADVANCED).build()));
    }

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
        when(climberSearchService.getAllSearches()).thenReturn(Flux.just(ClimberSearch.builder().level(ClimberLevel.ADVANCED).build()));

        //When
        Flux<ClimberSearch> climberSearchFlux = climberSearchController.showAllClimberSearches();
        //Then
        StepVerifier.create(climberSearchFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }
}