package ru.dsoccer1980.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.dsoccer1980.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.dsoccer1980.TestData.GENRE1;
import static ru.dsoccer1980.TestData.GENRE2;


public class GenreRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    void populateData() {
        genreRepository.deleteAll().block();
        genreRepository.save(GENRE1).block();
        genreRepository.save(GENRE2).block();
    }

    @Test
    void findAll() {
        Flux<Genre> all = genreRepository.findAll();
        StepVerifier
                .create(all)
                .assertNext(genre -> assertNotNull(genre.getId()))
                .assertNext(genre -> assertNotNull(genre.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void findById() {
        Mono<Genre> genreById = genreRepository.findById(GENRE2.getId());
        StepVerifier
                .create(genreById)
                .assertNext(genre -> assertThat(genre.getId()).isEqualTo(GENRE2.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void getByWrongId() {
        Mono<Genre> genreById = genreRepository.findById("-1");
        StepVerifier
                .create(genreById)
                .verifyComplete();
    }

    @Test
    void save() {
        Mono<Genre> genreMono = genreRepository.save(new Genre("New genre"));
        StepVerifier
                .create(genreMono)
                .assertNext(genre -> assertNotNull(genre.getId()))
                .expectComplete()
                .verify();
    }


    @Test
    void saveExistGenre() {
        long sizeBeforeSave = genreRepository.findAll().toStream().count();
        Mono<Genre> genreMono = genreRepository.save(GENRE1);
        StepVerifier
                .create(genreMono)
                .assertNext(genre -> assertNotNull(genre.getId()))
                .expectComplete()
                .verify();

        assertThat(genreRepository.findAll().toStream().count()).isEqualTo(sizeBeforeSave);
    }

    @Test
    void deleteById() {
        long sizeBeforeSave = genreRepository.findAll().toStream().count();
        Mono<Void> deleteById = genreRepository.deleteById(GENRE2.getId());
        StepVerifier
                .create(deleteById)
                .verifyComplete();
        assertThat(genreRepository.findAll().toStream().count()).isEqualTo(sizeBeforeSave - 1);
    }

    @Test
    void deleteByWrongId() {
        long sizeBeforeSave = genreRepository.findAll().toStream().count();
        Mono<Void> deleteById = genreRepository.deleteById("123456789012345678901234");
        StepVerifier
                .create(deleteById)
                .verifyComplete();
        Flux<Genre> all = genreRepository.findAll();

        assertThat(genreRepository.findAll().toStream().count()).isEqualTo(sizeBeforeSave);
    }

    @Test
    void findByName() {
        Mono<Genre> genreByName = genreRepository.findByName(GENRE1.getName());
        StepVerifier
                .create(genreByName)
                .assertNext(genre -> assertThat(genre.getName()).isEqualTo(GENRE1.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    void getByWrongName() {
        Mono<Genre> genreByWrongName = genreRepository.findByName("Wrong name");
        StepVerifier
                .create(genreByWrongName)
                .verifyComplete();
    }


}
