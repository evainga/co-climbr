package de.coclimbr.climbersearch;

import static org.mockito.Mockito.times;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import de.coclimbr.SecurityConfig;
import de.coclimbr.climber.data.Climber;
import de.coclimbr.climber.data.ClimberLevel;
import de.coclimbr.climber.service.ClimberService;
import de.coclimbr.climbersearch.controller.ClimberSearchController;
import de.coclimbr.climbersearch.data.ClimberSearch;
import de.coclimbr.climbersearch.data.ClimberSearchRepository;
import de.coclimbr.climbersearch.data.Location;
import de.coclimbr.climbersearch.service.ClimberSearchService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@WebFluxTest(controllers = ClimberSearchController.class)
@Import({ ClimberSearchService.class, SecurityConfig.class })
class ClimberSearchControllerDocumentation {

    private static final String SEARCH_ID = "999";
    private static final String INITIALISING_CLIMBER_ID = "123";
    private static final ClimberSearch CLIMBER_SEARCH = new ClimberSearch(INITIALISING_CLIMBER_ID, LocalDateTime.now(), Location.BERTABLOCK, ClimberLevel.ADVANCED, null);
    private static final Climber CLIMBER = new Climber("climber name", ClimberLevel.ADVANCED);
    private static final String API_SEARCHES = "/data/searches";

    @MockBean
    ClimberSearchRepository climberSearchRepository;
    @MockBean
    ClimberService climberService;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp(ApplicationContext applicationContext, RestDocumentationContextProvider restDocumentation) {
        this.webTestClient = WebTestClient.bindToApplicationContext(applicationContext)
                .configureClient()
                .filter(documentationConfiguration(restDocumentation).snippets().withEncoding("UTF-8"))
                .build();
    }

    @Test
    void testCreateInvalidClimberSearchRequest() {
        String INVALID_CLIMBER_SEARCH = "climber search";

        webTestClient.post()
                .uri(API_SEARCHES)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(INVALID_CLIMBER_SEARCH))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void testCreateClimberSearch() {
        Mockito.when(climberSearchRepository.save(CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));
        Mockito.when(climberService.getClimber(INITIALISING_CLIMBER_ID)).thenReturn(Mono.just(CLIMBER));

        webTestClient.post()
                .uri(API_SEARCHES)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CLIMBER_SEARCH))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .consumeWith(document("create-search",
                        preprocessRequest(prettyPrint()),
                        requestFields(fieldWithPath("id").description("The id of a search"),
                                fieldWithPath("initialisingClimberId").description("The id of the initialising climber"),
                                fieldWithPath("date").description("The date when a climber wants to go"),
                                fieldWithPath("location").description("The location where a climber wants to go"),
                                fieldWithPath("level").description("The level of the climber"),
                                fieldWithPath("joiningClimberIds").description("The ids of other climbers who join"))));

        Mockito.verify(climberSearchRepository, times(1)).save(CLIMBER_SEARCH);
    }

    @Test
    void testCreateClimberSearchWithInvalidClimberId() {
        Mockito.when(climberSearchRepository.save(CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));
        Mockito.when(climberService.getClimber(INITIALISING_CLIMBER_ID)).thenReturn(Mono.empty());

        webTestClient.post()
                .uri(API_SEARCHES)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CLIMBER_SEARCH))
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testUpdateClimberSearch() {
        Mockito.when(climberSearchRepository.save(CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));
        Mockito.when(climberSearchRepository.findById(SEARCH_ID)).thenReturn(Mono.just(CLIMBER_SEARCH));
        Mockito.when(climberService.getClimber(INITIALISING_CLIMBER_ID)).thenReturn(Mono.just(CLIMBER));

        webTestClient.post()
                .uri(API_SEARCHES + "/" + SEARCH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CLIMBER_SEARCH))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("update-search",
                        preprocessRequest(prettyPrint()),
                        requestFields(fieldWithPath("id").description("The id of a search"),
                                fieldWithPath("initialisingClimberId").description("The id of the initialising climber"),
                                fieldWithPath("date").description("The date when a climber wants to go"),
                                fieldWithPath("location").description("The location where a climber wants to go"),
                                fieldWithPath("level").description("The level of the climber"),
                                fieldWithPath("joiningClimberIds").description("The ids of other climbers who join"))));

        Mockito.verify(climberSearchRepository, times(1)).save(CLIMBER_SEARCH);
    }

    @Test
    void testUpdateClimberSearchWithInvalidClimberId() {
        Mockito.when(climberSearchRepository.save(CLIMBER_SEARCH)).thenReturn(Mono.just(CLIMBER_SEARCH));
        Mockito.when(climberSearchRepository.findById(SEARCH_ID)).thenReturn(Mono.just(CLIMBER_SEARCH));
        Mockito.when(climberService.getClimber(INITIALISING_CLIMBER_ID)).thenReturn(Mono.empty());

        webTestClient.post()
                .uri(API_SEARCHES + "/" + SEARCH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CLIMBER_SEARCH))
                .exchange()
                .expectStatus()
                .isNotFound();
    }


    @Test
    void testGetClimberSearch() {
        Mockito.when(climberSearchRepository.findById(SEARCH_ID)).thenReturn(Mono.just(CLIMBER_SEARCH));

        webTestClient.get()
                .uri(API_SEARCHES + "/" + SEARCH_ID)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().consumeWith(document("get-search",
                preprocessResponse(prettyPrint()),
                responseFields(fieldWithPath("id").description("The id of a search"),
                        fieldWithPath("initialisingClimberId").description("The id of the initialising climber"),
                        fieldWithPath("date").description("The date when a climber wants to go"),
                        fieldWithPath("location").description("The location where a climber wants to go"),
                        fieldWithPath("level").description("The level of the climber"),
                        fieldWithPath("joiningClimberIds").description("The ids of other climbers who join"))));

        Mockito.verify(climberSearchRepository, times(1)).findById(SEARCH_ID);
    }

    @Test
    void testGetAllClimberSearches() {
        Mockito.when(climberSearchRepository.findAll()).thenReturn(Flux.just(CLIMBER_SEARCH, CLIMBER_SEARCH));

        webTestClient.get()
                .uri(API_SEARCHES)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("get-all-searches",
                        preprocessResponse(prettyPrint()),
                        responseFields(fieldWithPath("[0].id").description("The id of a search"),
                                fieldWithPath("[0].initialisingClimberId").description("The id of the initialising climber"),
                                fieldWithPath("[0].date").description("The date when a climber wants to go"),
                                fieldWithPath("[0].location").description("The location where a climber wants to go"),
                                fieldWithPath("[0].level").description("The level of the climber"),
                                fieldWithPath("[0].joiningClimberIds").description("The ids of other climbers who join"))));

        Mockito.verify(climberSearchRepository, times(1)).findAll();
    }

    @Test
    void testDeleteClimberSearch() {
        Mockito.when(climberSearchRepository.deleteById(SEARCH_ID)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/data/searches/" + SEARCH_ID)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("delete-search"));

        Mockito.verify(climberSearchRepository, times(1)).deleteById(SEARCH_ID);
    }

}