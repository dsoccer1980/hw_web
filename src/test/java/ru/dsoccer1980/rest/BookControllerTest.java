package ru.dsoccer1980.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.repository.BookRepository;
import ru.dsoccer1980.service.BookService;
import ru.dsoccer1980.web.rest.BookController;

import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
@WebFluxTest(BookController.class)
public class BookControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Test
    void test() {
        given(this.bookService.getAll())
                .willReturn(Flux.just(new Book("Книга", null, null)));
        this.webTestClient.get().uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json("[{\"id\":null,\"name\":\"Книга\"}]").returnResult();
    }
}
