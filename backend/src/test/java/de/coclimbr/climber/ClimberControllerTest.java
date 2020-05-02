package de.coclimbr.climber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import de.coclimbr.climber.controller.ClimberController;
import de.coclimbr.climber.data.Climber;
import de.coclimbr.climber.data.ClimberLevel;
import de.coclimbr.climber.service.ClimberService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ClimberControllerTest {

    private static final Climber CLIMBER = new Climber("Berta", ClimberLevel.ADVANCED);
    private static final String ID = "123";

    private final ClimberService climberService = mock(ClimberService.class);
    private final ClimberController climberController = new ClimberController(climberService);

    @Test
    void testGetAllClimbersWhenWithoutClimber() {
        //Given
        when(climberService.getAllClimbers()).thenReturn(Flux.just());

        //When
        Flux<Climber> climberFlux = climberController.showAllClimbers();

        //Then
        StepVerifier.create(climberFlux)
                .verifyComplete();
    }

    @Test
    void testGetAllClimbersWhenHasClimber() {
        //Given
        when(climberService.getAllClimbers()).thenReturn(Flux.just(CLIMBER));

        //When
        Flux<Climber> climberFlux = climberController.showAllClimbers();

        //Then
        StepVerifier.create(climberFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }

    @Test
    void testGetClimber() {
        //Given
        when(climberService.getClimber(ID)).thenReturn(Mono.just(CLIMBER));

        //When
        Mono<Climber> climberFlux = climberController.getClimber(ID);

        //Then
        StepVerifier.create(climberFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }

    @Test
    void testDeleteClimber() {
        //Given
        when(climberService.deleteClimber(ID)).thenReturn(Mono.empty());

        //When
        Mono<Void> climberFlux = climberController.deleteClimber(ID);

        //Then
        StepVerifier.create(climberFlux)
                .verifyComplete();
    }

    @Test
    void testUpdateClimber() {
        //Given
        when(climberService.updateClimber(ID, CLIMBER)).thenReturn(Mono.just(CLIMBER));

        //When
        Mono<Climber> climberFlux = climberController.updateClimber(ID, CLIMBER);

        //Then
        StepVerifier.create(climberFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }

    @Test
    void testCreateClimber() {
        //Given
        when(climberService.createClimber(CLIMBER)).thenReturn(Mono.just(CLIMBER));

        //When
        Mono<Climber> climberFlux = climberController.createClimber(CLIMBER);

        //Then
        StepVerifier.create(climberFlux)
                .assertNext(firstEntry -> assertThat(firstEntry.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }
}