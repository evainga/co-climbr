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

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ClimberSearchController.class)
@Import(ClimberSearchService.class)
class ClimberSearchControllerIT {

    private static final String SEARCH_ID = "999";
    private static final String INITIALISING_CLIMBER_ID = "123";
    private static final ClimberSearch CLIMBER_SEARCH = new ClimberSearch(INITIALISING_CLIMBER_ID, LocalDateTime.now(), Location.BERTABLOCK, ClimberLevel.ADVANCED, null);

    @MockBean
    ClimberSearchRepository repository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreateInvalidClimberSearchRequest() {
        String INVALID_CLIMBER_SEARCH = "climber search";

        webTestClient.post()
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

        webTestClient.post()
                .uri("/searches")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CLIMBER_SEARCH))
                .exchange()
                .expectStatus()
                .isCreated();

        Mockito.verify(repository, times(1)).save(CLIMBER_SEARCH);
    }

    @Test
    void testUpdateClimberSearch() {
        Mockito.when(repository.save(CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));
        Mockito.when(repository.findById(SEARCH_ID)).thenReturn(Mono.just(CLIMBER_SEARCH));

        webTestClient.post()
                .uri("/searches/" + SEARCH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CLIMBER_SEARCH))
                .exchange()
                .expectStatus()
                .isOk();

        Mockito.verify(repository, times(1)).save(CLIMBER_SEARCH);
    }

    @Test
    void testGetClimberSearch() {
        Mockito.when(repository.findById(SEARCH_ID)).thenReturn(Mono.just(CLIMBER_SEARCH));

        webTestClient.get()
                .uri("/searches/" + SEARCH_ID)
                .exchange()
                .expectStatus()
                .isOk();

        Mockito.verify(repository, times(1)).findById(SEARCH_ID);
    }

    @Test
    void testGetAllClimberSearches() {
        Mockito.when(repository.findAll()).thenReturn(Flux.just(CLIMBER_SEARCH, CLIMBER_SEARCH));

        webTestClient.get()
                .uri("/searches")
                .exchange()
                .expectStatus()
                .isOk();

        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    void testDeleteClimberSearch() {
        Mockito.when(repository.deleteById(SEARCH_ID)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/searches/" + SEARCH_ID)
                .exchange()
                .expectStatus()
                .isOk();

        Mockito.verify(repository, times(1)).deleteById(SEARCH_ID);
    }

}