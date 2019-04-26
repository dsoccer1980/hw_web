package ru.dsoccer1980.service;

import com.github.cloudyrock.mongock.Mongock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.dsoccer1980.domain.Book;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
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
        List<Book> books = bookService.getAll();
        assertThat(books.toString()).isEqualTo(Arrays.asList(BOOK1, BOOK2, BOOK3).toString());
    }

    @Test
    void get() {
        Book book = bookService.get(BOOK1.getId());
        assertThat(book).isEqualTo(BOOK1);
    }

    @Test
    void addNewBook() {
        Book newBook = new Book("Новая книга", null, null);
        bookService.save(newBook, null, null);
        List<String> books = bookService.getAll().stream().map(Book::getName).collect(Collectors.toList());
        assertThat(books.toString()).isEqualTo(Arrays.asList(BOOK1.getName(), BOOK2.getName(), BOOK3.getName(), newBook.getName()).toString());
    }

    @Test
    void updateExistBook() {
        Book book = bookService.get(BOOK1.getId());
        book.setName("Новое имя");
        bookService.save(book, null, null);
        List<Book> books = bookService.getAll();
        assertThat(books.toString()).isEqualTo(Arrays.asList(book, BOOK2, BOOK3).toString());
    }

    @Test
    void delete() {
        bookService.delete(BOOK1.getId());
        List<Book> books = bookService.getAll();
        assertThat(books.toString()).isEqualTo(Arrays.asList(BOOK2, BOOK3).toString());
    }


}
