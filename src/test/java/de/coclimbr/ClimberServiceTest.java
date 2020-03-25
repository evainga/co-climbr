package de.coclimbr;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ClimberServiceTest {

    @Test
    void createClimber() {
        //Given
        var service = new ClimberService();

        //When
        ClimberSearch climberSearch = service.createSearch(LocalDateTime.now(), Climber.builder().build(), Location.BERTABLOCK);

        //Then
        assertThat(climberSearch.getLocation()).isEqualTo(Location.BERTABLOCK);
    }

    @Test
    void createEmptyClimberWhenNoInput() {
        //Given
        var service = new ClimberService();

        //When
        ClimberSearch climberSearch = service.createSearch(null, null, null);

        //Then
        assertThat(climberSearch).isNull();
    }

    @Test
    void createEmptyClimberWhenOnlyOneInputIsMissing() {
        //Given
        var service = new ClimberService();

        //When
        ClimberSearch climberSearch = service.createSearch(null, Climber.builder().build(), Location.BERTABLOCK);

        //Then
        assertThat(climberSearch).isNull();
    }


}