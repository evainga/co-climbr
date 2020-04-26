package de.coclimbr.climbersearch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.coclimbr.climber.data.ClimberLevel;
import de.coclimbr.climbersearch.data.ClimberSearch;
import de.coclimbr.climbersearch.data.ClimberSearchRepository;
import de.coclimbr.climbersearch.data.Location;
import de.coclimbr.climbersearch.service.ClimberSearchService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

class ClimberSearchServiceTest {

    private static final String INITIALISING_CLIMBER_ID = "123";
    private static final List<String> JOINING_CLIMBER_IDS = List.of("999");
    private static final LocalDateTime NEW_DATE = LocalDateTime.of(2020, 11, 1, 8, 30);

    private static final ClimberSearch CLIMBER_SEARCH = new ClimberSearch(INITIALISING_CLIMBER_ID, LocalDateTime.now(), Location.BERTABLOCK, ClimberLevel.ADVANCED, null);
    private static final ClimberSearch UPDATED_CLIMBER_SEARCH = new ClimberSearch(INITIALISING_CLIMBER_ID, NEW_DATE, Location.BOULDERKLUB, ClimberLevel.MEDIUM, JOINING_CLIMBER_IDS);
    private final ClimberSearchRepository climberSearchRepository = mock(ClimberSearchRepository.class);
    private ClimberSearchService climberSearchService;

    @BeforeEach
    void init() {
        climberSearchService = new ClimberSearchService(climberSearchRepository);
    }

    @Test
    void createClimberSearch() {
        //Given
        when(climberSearchRepository.save(CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));

        //When
        Mono<ClimberSearch> climberSearch = climberSearchService.createSearch(CLIMBER_SEARCH);

        //Then
        StepVerifier.create(climberSearch)
                .assertNext(search -> assertThat(search.getLocation()).isEqualTo(Location.BERTABLOCK))
                .verifyComplete();
    }

    @Test
    void getSpecificSearch() {
        //Given
        String searchId = "567";
        when(climberSearchRepository.findById(searchId)).thenReturn(Mono.just(CLIMBER_SEARCH));

        //When
        Mono<ClimberSearch> climberSearch = climberSearchService.getSearch(searchId);

        //Then
        StepVerifier.create(climberSearch)
                .assertNext(search -> assertThat(search.getLocation()).isEqualTo(Location.BERTABLOCK))
                .verifyComplete();
    }

    @Test
    void getAllSearches() {
        //Given
        when(climberSearchRepository.findAll()).thenReturn(Flux.just(CLIMBER_SEARCH, CLIMBER_SEARCH));

        //When
        Flux<ClimberSearch> climberSearches = climberSearchService.getAllSearches();

        //Then
        StepVerifier.create(climberSearches)
                .assertNext(search -> assertThat(search.getLocation()).isEqualTo(Location.BERTABLOCK))
                .assertNext(search -> assertThat(search.getLocation()).isEqualTo(Location.BERTABLOCK))
                .verifyComplete();
    }

    @Test
    void deleteSearch() {
        //Given
        String searchId = "567";
        when(climberSearchRepository.deleteById(searchId)).thenReturn(Mono.empty());

        //When
        Mono<Void> climberSearch = climberSearchService.deleteSearch(searchId);

        //Then
        StepVerifier.create(climberSearch)
                .verifyComplete();
    }

    @Test
    void updateSearch() {
        //Given
        String searchId = "567";
        ClimberSearch initialSearch = new ClimberSearch(INITIALISING_CLIMBER_ID, LocalDateTime.now(), Location.BERTABLOCK, ClimberLevel.ADVANCED, null);

        when(climberSearchRepository.findById(searchId)).thenReturn(Mono.just(initialSearch));
        when(climberSearchRepository.save(UPDATED_CLIMBER_SEARCH)).thenReturn(Mono.just(UPDATED_CLIMBER_SEARCH));

        //When
        Mono<ClimberSearch> climberSearch = climberSearchService.updateSearch(searchId, UPDATED_CLIMBER_SEARCH);

        //Then
        StepVerifier.create(climberSearch)
                .assertNext(search -> {
                    assertThat(search.getDate()).isEqualTo(NEW_DATE);
                    assertThat(search.getLocation()).isEqualTo(Location.BOULDERKLUB);
                    assertThat(search.getLevel()).isEqualTo(ClimberLevel.MEDIUM);
                    assertThat(search.getJoiningClimberIds()).isEqualTo(JOINING_CLIMBER_IDS);
                })
                .verifyComplete();
    }

    @Test
    void updateEmptySearch() {
        //Given
        String searchId = "567";
        when(climberSearchRepository.findById(searchId)).thenReturn(Mono.empty());
        when(climberSearchRepository.save(UPDATED_CLIMBER_SEARCH)).thenReturn(Mono.just(UPDATED_CLIMBER_SEARCH));

        //When
        Mono<ClimberSearch> climberSearch = climberSearchService.updateSearch(searchId, UPDATED_CLIMBER_SEARCH);

        //Then
        StepVerifier.create(climberSearch)
                .assertNext(search -> {
                    assertThat(search.getDate()).isEqualTo(NEW_DATE);
                    assertThat(search.getLocation()).isEqualTo(Location.BOULDERKLUB);
                    assertThat(search.getLevel()).isEqualTo(ClimberLevel.MEDIUM);
                    assertThat(search.getJoiningClimberIds()).isEqualTo(JOINING_CLIMBER_IDS);
                })
                .verifyComplete();
    }
}