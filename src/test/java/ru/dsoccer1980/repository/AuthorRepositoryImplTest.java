package ru.dsoccer1980.repository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.dsoccer1980.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.dsoccer1980.TestData.*;


public class AuthorRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void populate() {
        authorRepository.deleteAll().block();
        authorRepository.save(AUTHOR1).block();
        authorRepository.save(AUTHOR2).block();
        authorRepository.save(AUTHOR3).block();
    }

    @Test
    void findAll() {
        Flux<Author> all = authorRepository.findAll();
        StepVerifier
                .create(all)
                .assertNext(author -> assertNotNull(author.getId()))
                .assertNext(author -> assertNotNull(author.getId()))
                .assertNext(author -> assertNotNull(author.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void findById() {
        Mono<Author> authorById = authorRepository.findById(AUTHOR2.getId());
        StepVerifier
                .create(authorById)
                .assertNext(author -> assertThat(author.getId()).isEqualTo(AUTHOR2.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void getByWrongId() {
        Mono<Author> authorById = authorRepository.findById("-1");
        StepVerifier
                .create(authorById)
                .verifyComplete();
    }

    @Test
    void save() {
        Mono<Author> authorMono = authorRepository.save(new Author("New author"));
        StepVerifier
                .create(authorMono)
                .assertNext(author -> assertNotNull(author.getId()))
                .expectComplete()
                .verify();
    }


    @Test
    void saveExistAuthor() {
        long sizeBeforeSave = authorRepository.findAll().toStream().count();
        Mono<Author> authorMono = authorRepository.save(AUTHOR1);
        StepVerifier
                .create(authorMono)
                .assertNext(author -> assertNotNull(author.getId()))
                .expectComplete()
                .verify();

        assertThat(authorRepository.findAll().toStream().count()).isEqualTo(sizeBeforeSave);
    }

    @Test
    void deleteById() {
        long sizeBeforeSave = authorRepository.findAll().toStream().count();
        Mono<Void> deleteById = authorRepository.deleteById(AUTHOR2.getId());
        StepVerifier
                .create(deleteById)
                .verifyComplete();

        assertThat(authorRepository.findAll().toStream().count()).isEqualTo(sizeBeforeSave - 1);
    }

    @Test
    void deleteByWrongId() {
        long sizeBeforeSave = authorRepository.findAll().toStream().count();
        Mono<Void> deleteById = authorRepository.deleteById("123456789012345678901234");
        StepVerifier
                .create(deleteById)
                .verifyComplete();

        assertThat(authorRepository.findAll().toStream().count()).isEqualTo(sizeBeforeSave);
    }

    @Test
    void findByName() {
        Mono<Author> authorByName = authorRepository.findByName(AUTHOR1.getName());
        StepVerifier
                .create(authorByName)
                .assertNext(author -> assertThat(author.getName()).isEqualTo(AUTHOR1.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    void getByWrongName() {
        Mono<Author> authorByWrongName = authorRepository.findByName("Wrong name");
        StepVerifier
                .create(authorByWrongName)
                .verifyComplete();
    }

}
