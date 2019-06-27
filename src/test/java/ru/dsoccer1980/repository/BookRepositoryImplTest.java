package ru.dsoccer1980.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.util.exception.NotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();
        authorRepository.save(AUTHOR1);
        authorRepository.save(AUTHOR2);
        authorRepository.save(AUTHOR3);
        genreRepository.save(GENRE1);
        genreRepository.save(GENRE2);
        BOOK1.setAuthor(AUTHOR1);
        BOOK2.setAuthor(AUTHOR2);
        BOOK3.setAuthor(AUTHOR3);
        BOOK1.setGenre(GENRE1);
        BOOK2.setGenre(GENRE1);
        BOOK3.setGenre(GENRE2);
        bookRepository.save(BOOK1);
        bookRepository.save(BOOK2);
        bookRepository.save(BOOK3);
    }

    @Test
    void findAll() {
        assertThat(bookRepository.findAll().toString()).isEqualTo(Arrays.asList(BOOK1, BOOK2, BOOK3).toString());
    }

    @Test
    void findById() {
        Book book = bookRepository.findById(BOOK1.getId()).orElseThrow(() -> new NotFoundException("Book not found"));
        assertThat(book.getId()).isEqualTo(BOOK1.getId());
    }

    @Test
    void getByWrongId() {
        assertThrows(NotFoundException.class, () -> bookRepository.findById("-1").orElseThrow(() -> new NotFoundException("Book not found")));
    }

    @Test
    void insert() {
        int sizeBeforeInsert = bookRepository.findAll().size();
        bookRepository.save(NEW_BOOK);
        assertThat(bookRepository.findAll().size()).isEqualTo(sizeBeforeInsert + 1);
    }

    @Test
    void insertExistBook() {
        int sizeBeforeInsert = bookRepository.findAll().size();
        bookRepository.save(BOOK1);
        assertThat(bookRepository.findAll().size()).isEqualTo(sizeBeforeInsert);
    }

    @Test
    void deleteById() {
        int sizeBeforeDelete = bookRepository.findAll().size();
        bookRepository.deleteById(BOOK1.getId());
        assertThat(bookRepository.findAll().size()).isEqualTo(sizeBeforeDelete - 1);
    }

    @Test
    void getByAuthorId() {
        assertThat(bookRepository.findByAuthorId(AUTHOR1.getId()).toString()).isEqualTo(Arrays.asList(BOOK1).toString());
    }

    @Test
    void getByGenreId() {
        assertThat(bookRepository.findByGenreId(GENRE1.getId()).toString()).isEqualTo(Arrays.asList(BOOK1, BOOK2).toString());
    }

    @Test
    @DisplayName("При удалении автора он должен удалиться из книги")
    void deleteAuthor() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        Author newAuthor = authorRepository.save(new Author("new author"));
        Genre newGenre = genreRepository.save(new Genre("new genre"));

        bookRepository.save(new Book("new Book", newAuthor, newGenre));

        Book book = bookRepository.findAll().get(0);
        Author author = authorRepository.findAll().get(0);
        assertThat(book.getAuthor().getId()).isEqualTo(author.getId());

        authorRepository.deleteById(author.getId());

        Optional<Book> byId = bookRepository.findById(book.getId());

        assertThat(authorRepository.findAll().size()).isEqualTo(0);
        assertThat(byId.get().getAuthor()).isEqualTo(null);
    }

    @Test
    @DisplayName("При удалении жанра он должен удалиться из книги")
    void deleteGenre() {
        bookRepository.deleteAll();
        genreRepository.deleteAll();

        Author newAuthor = authorRepository.save(new Author("new author"));
        Genre newGenre = genreRepository.save(new Genre("new genre"));

        bookRepository.save(new Book("new Book", newAuthor, newGenre));

        Book book = bookRepository.findAll().get(0);
        Genre genre = genreRepository.findAll().get(0);
        assertThat(book.getGenre().getId()).isEqualTo(genre.getId());

        genreRepository.deleteById(genre.getId());

        Optional<Book> byId = bookRepository.findById(book.getId());

        assertThat(genreRepository.findAll().size()).isEqualTo(0);
        assertThat(byId.get().getGenre()).isEqualTo(null);
    }

    @Test
    @DisplayName("При изменении имени автора он должен измениться в книге")
    void changeAuthorName() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        Author newAuthor = authorRepository.save(new Author("new author"));
        Genre newGenre = genreRepository.save(new Genre("new genre"));

        bookRepository.save(new Book("new Book", newAuthor, newGenre));

        Book book = bookRepository.findAll().get(0);
        Author author = authorRepository.findAll().get(0);
        assertThat(book.getAuthor().getId()).isEqualTo(author.getId());

        author.setName("New name author");
        authorRepository.save(author);
        Author author1 = authorRepository.findAll().get(0);

        List<Book> byAuthorId = bookRepository.findByAuthorId(author1.getId());
        assertThat(byAuthorId.get(0).getAuthor().getName()).isEqualTo(author1.getName());
    }
}

