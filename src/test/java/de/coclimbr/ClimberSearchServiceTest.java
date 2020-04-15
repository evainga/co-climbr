package de.coclimbr;

import de.coclimbr.service.ClimberSearch;
import de.coclimbr.service.ClimberSearchService;
import de.coclimbr.service.Location;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ClimberSearchServiceTest {

    @Test
    void createClimber() {
        //Given
        var service = new ClimberSearchService();

        //When
        Mono<ClimberSearch> climberSearch = service.createSearch(ClimberSearch.builder().date(LocalDateTime.now()).initialisingClimber(Climber.builder().build()).location(Location.BERTABLOCK).build());

        //Then
        StepVerifier.create(climberSearch)
                .assertNext(search -> assertThat(search.getLocation()).isEqualTo(Location.BERTABLOCK))
                .verifyComplete();
    }

    @Test
    void createEmptyClimberWhenNoInput() {
        //Given
        var service = new ClimberSearchService();

        //When
        Mono<ClimberSearch> climberSearch = service.createSearch(ClimberSearch.builder().build());

        //Then
        assertThat(climberSearch).isNull();
    }

    @Test
    void createEmptyClimberWhenOnlyOneInputIsMissing() {
        //Given
        var service = new ClimberSearchService();

        //When
        Mono<ClimberSearch> climberSearch = service.createSearch(ClimberSearch.builder().location(Location.BERTABLOCK).initialisingClimber(Climber.builder().build()).build());

        //Then
        assertThat(climberSearch).isNull();
    }


}