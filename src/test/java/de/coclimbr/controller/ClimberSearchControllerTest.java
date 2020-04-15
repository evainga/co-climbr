package de.coclimbr.controller;

import de.coclimbr.service.ClimberLevel;
import de.coclimbr.service.ClimberSearch;
import de.coclimbr.service.ClimberSearchService;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ClimberSearchControllerTest {

    ClimberSearchService climberSearchService = mock(ClimberSearchService.class);
    private ClimberSearchController climberSearchController = new ClimberSearchController(climberSearchService);

    @Test
    void testEmpty() {
        //Given
        //When
        Flux<ClimberSearch> climberSearchFlux = climberSearchController.showAllClimberSearches();
        //Then
        assertThat(climberSearchFlux).isNull();
    }

    @Test
    void testFlux() {
        //Given

        //When
        Flux<ClimberSearch> climberSearchFlux = climberSearchController.showAllClimberSearches();
        //Then
        StepVerifier.create(climberSearchFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }
}