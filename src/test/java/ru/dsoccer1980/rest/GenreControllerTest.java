package ru.dsoccer1980.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.service.GenreService;
import ru.dsoccer1980.web.rest.GenreController;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private GenreService genreService;

    @Test
    void test() {
        given(this.genreService.getAll())
                .willReturn(Flux.just(new Genre("Фантастика")));
        this.webTestClient.get().uri("/genre")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[{\"id\":null,\"name\":\"Фантастика\"}]").returnResult();
    }
}
