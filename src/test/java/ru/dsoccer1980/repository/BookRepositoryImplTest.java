package ru.dsoccer1980.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.dsoccer1980.TestData.*;


public class BookRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    GenreRepository genreRepository;

    @BeforeEach
    void populateData() {
        bookRepository.deleteAll().block();
        authorRepository.deleteAll().block();
        genreRepository.deleteAll().block();
        authorRepository.save(AUTHOR1).block();
        authorRepository.save(AUTHOR2).block();
        authorRepository.save(AUTHOR3).block();
        genreRepository.save(GENRE1).block();
        genreRepository.save(GENRE2).block();
        BOOK1.setAuthor(AUTHOR1);
        BOOK2.setAuthor(AUTHOR2);
        BOOK3.setAuthor(AUTHOR3);
        BOOK1.setGenre(GENRE1);
        BOOK2.setGenre(GENRE1);
        BOOK3.setGenre(GENRE2);
        bookRepository.save(BOOK1).block();
        bookRepository.save(BOOK2).block();
        bookRepository.save(BOOK3).block();
    }

    @Test
    void findAll() {
        Flux<Book> all = bookRepository.findAll();
        StepVerifier
                .create(all)
                .assertNext(book -> assertNotNull(book.getId()))
                .assertNext(book -> assertNotNull(book.getId()))
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void findById() {
        Mono<Book> bookById = bookRepository.findById(BOOK1.getId());
        StepVerifier
                .create(bookById)
                .assertNext(book -> assertThat(book.getId()).isEqualTo(BOOK1.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    void getByWrongId() {
        Mono<Book> bookById = bookRepository.findById("-1");
        StepVerifier
                .create(bookById)
                .expectComplete()
                .verify();
    }

    @Test
    void save() {
        long sizeBeforeSave = bookRepository.findAll().toStream().count();
        Mono<Book> bookMono = bookRepository.save(NEW_BOOK);
        StepVerifier
                .create(bookMono)
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();
        assertThat(bookRepository.findAll().toStream().count()).isEqualTo(sizeBeforeSave + 1);
    }

    @Test
    void saveExistBook() {
        long sizeBeforeSave = bookRepository.findAll().toStream().count();
        Mono<Book> bookMono = bookRepository.save(BOOK1);
        StepVerifier
                .create(bookMono)
                .assertNext(book -> assertNotNull(book.getId()))
                .expectComplete()
                .verify();

        assertThat(bookRepository.findAll().toStream().count()).isEqualTo(sizeBeforeSave);
    }

    @Test
    void deleteById() {
        long sizeBeforeDelete = bookRepository.findAll().toStream().count();
        Mono<Void> deleteById = bookRepository.deleteById(BOOK1.getId());
        StepVerifier
                .create(deleteById)
                .verifyComplete();
        assertThat(bookRepository.findAll().toStream().count()).isEqualTo(sizeBeforeDelete - 1);
    }

    @Test
    void getByAuthorId() {
        Flux<Book> byAuthorId = bookRepository.findByAuthorId(AUTHOR1.getId());
        StepVerifier
                .create(byAuthorId)
                .assertNext(book -> assertThat(book).isEqualTo(BOOK1))
                .expectComplete()
                .verify();
    }

    @Test
    void getByGenreId() {
        Flux<Book> byGenreId = bookRepository.findByGenreId(GENRE1.getId());
        StepVerifier
                .create(byGenreId)
                .assertNext(book -> assertThat(book).isEqualTo(BOOK1))
                .assertNext(book -> assertThat(book).isEqualTo(BOOK2))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("При удалении автора он должен удалиться из книги")
    void deleteAuthor() {
        bookRepository.deleteAll().block();
        authorRepository.deleteAll().block();

        Author newAuthor = authorRepository.save(new Author("new author")).block();
        Genre newGenre = genreRepository.save(new Genre("new genre")).block();
        bookRepository.save(new Book("new Book", newAuthor, newGenre)).block();

        Author authorWithId = authorRepository.findAll().blockFirst();
        Book bookWithId = bookRepository.findAll().blockFirst();
        assertThat(bookWithId.getAuthor().getId()).isEqualTo(authorWithId.getId());

        authorRepository.deleteById(authorWithId.getId()).block();

        Mono<Book> bookById = bookRepository.findById(bookWithId.getId());
        Flux<Author> authors = authorRepository.findAll();
        StepVerifier
                .create(authors)
                .verifyComplete();
        StepVerifier
                .create(bookById)
                .assertNext(book -> assertThat(book.getAuthor()).isEqualTo(null))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("При удалении жанра он должен удалиться из книги")
    void deleteGenre() {
        bookRepository.deleteAll().block();
        genreRepository.deleteAll().block();

        Author newAuthor = authorRepository.save(new Author("new author")).block();
        Genre newGenre = genreRepository.save(new Genre("new genre")).block();
        bookRepository.save(new Book("new Book", newAuthor, newGenre)).block();

        Genre genreWithId = genreRepository.findAll().blockFirst();
        Book bookWithId = bookRepository.findAll().blockFirst();
        assertThat(bookWithId.getGenre().getId()).isEqualTo(genreWithId.getId());

        genreRepository.deleteById(genreWithId.getId()).block();

        Mono<Book> bookById = bookRepository.findById(bookWithId.getId());
        Flux<Genre> genres = genreRepository.findAll();
        StepVerifier
                .create(genres)
                .verifyComplete();
        StepVerifier
                .create(bookById)
                .assertNext(book -> assertThat(book.getGenre()).isEqualTo(null))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("При изменении имени автора он должен измениться в книге")
    void changeAuthorName() {
        bookRepository.deleteAll().block();
        authorRepository.deleteAll().block();

        Author newAuthor = authorRepository.save(new Author("new author")).block();
        Genre newGenre = genreRepository.save(new Genre("new genre")).block();
        bookRepository.save(new Book("new Book", newAuthor, newGenre)).block();

        Author authorWithId = authorRepository.findAll().blockFirst();
        Book bookWithId = bookRepository.findAll().blockFirst();
        assertThat(bookWithId.getAuthor().getId()).isEqualTo(authorWithId.getId());

        authorWithId.setName("New name author");
        Author savedAuthor = authorRepository.save(authorWithId).block();
        Flux<Book> findByAuthorId = bookRepository.findByAuthorId(savedAuthor.getId());
        StepVerifier
                .create(findByAuthorId)
                .assertNext(book -> assertThat(book.getAuthor().getName()).isEqualTo(authorWithId.getName()))
                .expectComplete()
                .verify();
    }

}
