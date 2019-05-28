package ru.dsoccer1980.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.service.AuthorService;
import ru.dsoccer1980.web.rest.AuthorController;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(AuthorController.class)
public class AuthorControllerTest {


    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private AuthorService authorService;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )

    @Test
    void test() {
        given(this.authorService.getAll())
                .willReturn(Flux.just(new Author("Стругацкий")));
        this.webTestClient.get().uri("/author")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[{\"id\":null,\"name\":\"Стругацкий\"}]").returnResult();
    }
}
