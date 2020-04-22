package de.coclimbr.controller;

import static org.mockito.Mockito.times;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import de.coclimbr.data.ClimberSearchRepository;
import de.coclimbr.service.ClimberLevel;
import de.coclimbr.service.ClimberSearch;
import de.coclimbr.service.ClimberSearchService;
import de.coclimbr.service.Location;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ClimberSearchController.class)
@Import(ClimberSearchService.class)
class ClimberSearchControllerIT {

    @MockBean
    ClimberSearchRepository repository;

    @Autowired
    private WebTestClient webClient;

    private static final ClimberSearch CLIMBER_SEARCH = ClimberSearch.builder()
            .initialisingClimberId(123L)
            .date(LocalDateTime.now())
            .location(Location.BERTABLOCK)
            .level(ClimberLevel.ADVANCED)
            .build();

    @Test
    void testCreateInvalidClimberSearchRequest() {
        String INVALID_CLIMBER_SEARCH = "climber search";

        webClient.post()
                .uri("/searches")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(INVALID_CLIMBER_SEARCH))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void testCreateClimberSearch() {
        Mockito.when(repository.save(CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));

        webClient.post()
                .uri("/searches")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CLIMBER_SEARCH))
                .exchange()
                .expectStatus()
                .isCreated();

        Mockito.verify(repository, times(1)).save(CLIMBER_SEARCH);
    }

}