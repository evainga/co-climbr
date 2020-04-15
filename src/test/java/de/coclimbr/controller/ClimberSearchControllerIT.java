package de.coclimbr.controller;

import de.coclimbr.data.ClimberSearchRepository;
import de.coclimbr.service.ClimberSearch;
import de.coclimbr.service.ClimberSearchService;
import de.coclimbr.service.Location;
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
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ClimberSearchController.class)
@Import(ClimberSearchService.class)
class ClimberSearchControllerIT {

    @MockBean
    ClimberSearchRepository repository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testCreateClimberSearch() {
        ClimberSearch climberSearch = ClimberSearch.builder().location(Location.BERTABLOCK).build();
        String bla = "p";


        Mockito.when(repository.save(climberSearch)).thenReturn(Mono.just(climberSearch));

        webClient.post()
                .uri("/searches")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bla))
                .exchange()
                .expectStatus()
                .isCreated();

        Mockito.verify(repository, times(1)).save(climberSearch);
    }

}