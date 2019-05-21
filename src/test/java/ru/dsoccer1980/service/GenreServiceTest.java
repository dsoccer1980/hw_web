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
import ru.dsoccer1980.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.dsoccer1980.TestData.GENRE1;
import static ru.dsoccer1980.TestData.GENRE2;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest
public class GenreServiceTest {

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
        Flux<Genre> genres = genreService.getAll();
        StepVerifier
                .create(genres)
                .assertNext(genre -> assertThat(genre).isEqualTo(GENRE1))
                .assertNext(genre -> assertThat(genre).isEqualTo(GENRE2))
                .expectComplete()
                .verify();
    }


    @Test
    void get() {
        Mono<Genre> genreMono = genreService.get(GENRE1.getId());
        StepVerifier
                .create(genreMono)
                .assertNext(genre -> assertThat(genre).isEqualTo(GENRE1))
                .expectComplete()
                .verify();
    }

    @Test
    void addNewGenre() {
        Genre newGenre = new Genre("Новый жанр");
        Mono<Genre> genreMono = genreService.save(newGenre);
        StepVerifier
                .create(genreMono)
                .assertNext(genre -> assertNotNull(genre.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void updateExistGenre() {
        Genre genreForUpdate = genreService.get(GENRE1.getId()).block();
        genreForUpdate.setName("Новое имя");
        Mono<Genre> genreMono = genreService.save(genreForUpdate);
        StepVerifier
                .create(genreMono)
                .assertNext(genre -> assertThat(genre.getName()).isEqualTo(genreForUpdate.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        Mono<Void> deleteMono = genreService.delete(GENRE1.getId());
        StepVerifier
                .create(deleteMono)
                .verifyComplete();

        Flux<Genre> genres = genreService.getAll();
        StepVerifier
                .create(genres)
                .assertNext(genre -> assertThat(genre).isEqualTo(GENRE2))
                .expectComplete()
                .verify();
    }
}
