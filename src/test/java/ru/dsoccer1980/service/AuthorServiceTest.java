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
import ru.dsoccer1980.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.dsoccer1980.TestData.*;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private Mongock mongock;

    @BeforeEach
    void init() {
        mongock.execute();
    }

    @Test
    void getAll() {
        Flux<Author> authors = authorService.getAll();
        StepVerifier
                .create(authors)
                .assertNext(author -> assertThat(author).isEqualTo(AUTHOR1))
                .assertNext(author -> assertThat(author).isEqualTo(AUTHOR2))
                .assertNext(author -> assertThat(author).isEqualTo(AUTHOR3))
                .expectComplete()
                .verify();
    }


    @Test
    void get() {
        Mono<Author> authorMono = authorService.get(AUTHOR1.getId());
        StepVerifier
                .create(authorMono)
                .assertNext(author -> assertThat(author).isEqualTo(AUTHOR1))
                .expectComplete()
                .verify();
    }

    @Test
    void addNewAuthor() {
        Author newAuthor = new Author("Новый автор");
        Mono<Author> authorMono = authorService.save(newAuthor);
        StepVerifier
                .create(authorMono)
                .assertNext(author -> assertNotNull(author.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateExistAuthor() {
        Author authorForUpdate = authorService.get(AUTHOR1.getId()).block();
        authorForUpdate.setName("Новое имя");
        Mono<Author> authorMono = authorService.save(authorForUpdate);
        StepVerifier
                .create(authorMono)
                .assertNext(author -> assertThat(author.getName()).isEqualTo(authorForUpdate.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mono<Void> deleteMono = authorService.delete(AUTHOR1.getId());
        StepVerifier
                .create(deleteMono)
                .verifyComplete();

        Flux<Author> authors = authorService.getAll();
        StepVerifier
                .create(authors)
                .assertNext(author -> assertThat(author).isEqualTo(AUTHOR2))
                .assertNext(author -> assertThat(author).isEqualTo(AUTHOR3))
                .expectComplete()
                .verify();
    }

}
