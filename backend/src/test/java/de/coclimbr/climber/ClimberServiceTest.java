package de.coclimbr.climber;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.coclimbr.climber.data.Climber;
import de.coclimbr.climber.data.ClimberLevel;
import de.coclimbr.climber.data.ClimberRepository;
import de.coclimbr.climber.service.ClimberService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ClimberServiceTest {

    private static final Climber CLIMBER = new Climber("Eva", ClimberLevel.ADVANCED);
    private static final Climber UPDATED_CLIMBER = new Climber("Eva", ClimberLevel.MEDIUM);

    private final ClimberRepository climberRepository = mock(ClimberRepository.class);
    private ClimberService climberService;

    @BeforeEach
    void init() {
        climberService = new ClimberService(climberRepository);
    }

    @Test
    void createClimber() {
        //Given
        when(climberRepository.save(CLIMBER)).thenReturn(Mono.just(CLIMBER));

        //When
        Mono<Climber> climber = climberService.createClimber(CLIMBER);

        //Then
        StepVerifier.create(climber)
                .assertNext(climbr -> assertThat(climbr.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }

    @Test
    void getSpecificClimber() {
        //Given
        String climberId = "567";
        when(climberRepository.findById(climberId)).thenReturn(Mono.just(CLIMBER));

        //When
        Mono<Climber> climber = climberService.getClimber(climberId);

        //Then
        StepVerifier.create(climber)
                .assertNext(climbr -> assertThat(climbr.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }

    @Test
    void getAllClimbers() {
        //Given
        when(climberRepository.findAll()).thenReturn(Flux.just(CLIMBER, CLIMBER));

        //When
        Flux<Climber> climberes = climberService.getAllClimbers();

        //Then
        StepVerifier.create(climberes)
                .assertNext(climber -> assertThat(climber.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .assertNext(climber -> assertThat(climber.getLevel()).isEqualTo(ClimberLevel.ADVANCED))
                .verifyComplete();
    }

    @Test
    void deleteClimber() {
        //Given
        String climberId = "567";
        when(climberRepository.deleteById(climberId)).thenReturn(Mono.empty());

        //When
        Mono<Void> climber = climberService.deleteClimber(climberId);

        //Then
        StepVerifier.create(climber)
                .verifyComplete();
    }

    @Test
    void updateClimber() {
        //Given
        String climberId = "567";
        Climber initialClimber = new Climber("Bert", ClimberLevel.ADVANCED);

        when(climberRepository.findById(climberId)).thenReturn(Mono.just(initialClimber));
        when(climberRepository.save(UPDATED_CLIMBER)).thenReturn(Mono.just(UPDATED_CLIMBER));

        //When
        Mono<Climber> climber = climberService.updateClimber(climberId, UPDATED_CLIMBER);

        //Then
        StepVerifier.create(climber)
                .assertNext(climbr -> {
                    assertThat(climbr.getName()).isEqualTo("Eva");
                    assertThat(climbr.getLevel()).isEqualTo(ClimberLevel.MEDIUM);
                })
                .verifyComplete();
    }

    @Test
    void updateEmptyClimber() {
        //Given
        String climberId = "567";
        when(climberRepository.findById(climberId)).thenReturn(Mono.empty());
        when(climberRepository.save(UPDATED_CLIMBER)).thenReturn(Mono.just(UPDATED_CLIMBER));

        //When
        Mono<Climber> climber = climberService.updateClimber(climberId, UPDATED_CLIMBER);

        //Then
        StepVerifier.create(climber)
                .assertNext(climbr -> {
                    assertThat(climbr.getName()).isEqualTo("Eva");
                    assertThat(climbr.getLevel()).isEqualTo(ClimberLevel.MEDIUM);
                })
                .verifyComplete();
    }
}