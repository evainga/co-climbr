package de.coclimbr.climber;

import static org.mockito.Mockito.times;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.document;
import static org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation.documentationConfiguration;

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

import de.coclimbr.climber.controller.ClimberController;
import de.coclimbr.climber.data.Climber;
import de.coclimbr.climber.data.ClimberLevel;
import de.coclimbr.climber.data.ClimberRepository;
import de.coclimbr.climber.service.ClimberService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class })
@WebFluxTest(controllers = ClimberController.class)
@Import(ClimberService.class)
class ClimberControllerDocumentation {

    private static final String CLIMBER_ID = "999";
    private static final Climber CLIMBER = new Climber("Bert", ClimberLevel.ADVANCED);
    private static final String DATA_CLIMBERS = "/data/climbers";

    @MockBean
    ClimberRepository repository;

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
    void testCreateInvalidClimberRequest() {
        String INVALID_CLIMBER_CLIMBER = "climber climber";

        webTestClient.post()
                .uri(DATA_CLIMBERS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(INVALID_CLIMBER_CLIMBER))
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

    @Test
    void testCreateClimber() {
        Mockito.when(repository.save(CLIMBER)).thenReturn(Mono.just(CLIMBER));

        webTestClient.post()
                .uri(DATA_CLIMBERS)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CLIMBER))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .consumeWith(document("create-climber",
                        preprocessRequest(prettyPrint()),
                        requestFields(fieldWithPath("id").description("The id of a climber"),
                                fieldWithPath("name").description("The name of the initialising climber"),
                                fieldWithPath("level").description("The level of the climber"))));

        Mockito.verify(repository, times(1)).save(CLIMBER);
    }

    @Test
    void testUpdateClimber() {
        Mockito.when(repository.save(CLIMBER)).thenReturn(Mono.just(CLIMBER));
        Mockito.when(repository.findById(CLIMBER_ID)).thenReturn(Mono.just(CLIMBER));

        webTestClient.post()
                .uri(DATA_CLIMBERS + "/" + CLIMBER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(CLIMBER))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("update-climber",
                        preprocessRequest(prettyPrint()),
                        requestFields(fieldWithPath("id").description("The id of a climber"),
                                fieldWithPath("name").description("The name of the initialising climber"),
                                fieldWithPath("level").description("The level of the climber"))));

        Mockito.verify(repository, times(1)).save(CLIMBER);
    }

    @Test
    void testGetClimber() {
        Mockito.when(repository.findById(CLIMBER_ID)).thenReturn(Mono.just(CLIMBER));

        webTestClient.get()
                .uri(DATA_CLIMBERS + "/" + CLIMBER_ID)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().consumeWith(document("get-climber",
                preprocessResponse(prettyPrint()),
                responseFields(fieldWithPath("id").description("The id of a climber"),
                        fieldWithPath("name").description("The name of the initialising climber"),
                        fieldWithPath("level").description("The level of the climber"))));

        Mockito.verify(repository, times(1)).findById(CLIMBER_ID);
    }

    @Test
    void testGetAllClimbers() {
        Mockito.when(repository.findAll()).thenReturn(Flux.just(CLIMBER, CLIMBER));

        webTestClient.get()
                .uri(DATA_CLIMBERS)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("get-all-climbers",
                        preprocessResponse(prettyPrint()),
                        responseFields(fieldWithPath("[0].id").description("The id of a climber"),
                                fieldWithPath("[0].name").description("The name of the initialising climber"),
                                fieldWithPath("[0].level").description("The level of the climber"))));

        Mockito.verify(repository, times(1)).findAll();
    }

    @Test
    void testDeleteClimber() {
        Mockito.when(repository.deleteById(CLIMBER_ID)).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri(DATA_CLIMBERS + "/" + CLIMBER_ID)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(document("delete-climber"));

        Mockito.verify(repository, times(1)).deleteById(CLIMBER_ID);
    }

}