package ru.dsoccer1980.service;

import com.github.cloudyrock.mongock.Mongock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.dsoccer1980.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.dsoccer1980.TestData.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private Mongock mongock;

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @Test
    void getAll() {
        Flux<Book> books = bookService.getAll();
        StepVerifier
                .create(books)
                .assertNext(book -> assertThat(book).isEqualTo(BOOK1))
                .assertNext(book -> assertThat(book).isEqualTo(BOOK2))
                .assertNext(book -> assertThat(book).isEqualTo(BOOK3))
                .expectComplete()
                .verify();
    }

    @Test
    void get() {
        Mono<Book> bookMono = bookService.get(BOOK1.getId());
        StepVerifier
                .create(bookMono)
                .assertNext(book -> assertThat(book).isEqualTo(BOOK1))
                .expectComplete()
                .verify();
    }


    @Test
    void addNewBook() {
        Book newBook = new Book("Новая книга", null, null);
        Mono<Book> bookMono = bookService.save(newBook, null, null);
        StepVerifier
                .create(bookMono)
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateExistBook() {
        Book bookForUpdate = bookService.get(BOOK1.getId()).block();
        bookForUpdate.setName("Новое имя");
        Mono<Book> bookMono = bookService.save(bookForUpdate, null, null);
        StepVerifier
                .create(bookMono)
                .assertNext(book -> assertThat(book.getName()).isEqualTo(bookForUpdate.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mono<Void> deleteMono = bookService.delete(BOOK1.getId());
        StepVerifier
                .create(deleteMono)
                .verifyComplete();

        Flux<Book> bookFlux = bookService.getAll();
        StepVerifier
                .create(bookFlux)
                .assertNext(book -> assertThat(book).isEqualTo(BOOK2))
                .assertNext(book -> assertThat(book).isEqualTo(BOOK3))
                .expectComplete()
                .verify();
    }
}
