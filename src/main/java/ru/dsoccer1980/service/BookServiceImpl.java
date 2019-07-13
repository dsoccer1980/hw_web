package ru.dsoccer1980.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.repository.AuthorRepository;
import ru.dsoccer1980.repository.BookRepository;
import ru.dsoccer1980.repository.GenreRepository;
import ru.dsoccer1980.util.exception.NotFoundException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final MeterRegistry registry;
    private Counter bookSaveCounter;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, GenreRepository genreRepository, MeterRegistry registry) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.genreRepository = genreRepository;
        this.registry = registry;
        bookSaveCounter = registry.counter("services.book.save");
    }

    @HystrixCommand(fallbackMethod = "getDefaultBooks", groupKey = "BookService", commandKey = "getAll")
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book get(String id) {
        Book book;
        if (id.equals("-1")) {
            book = new Book("Имя книги", new Author("Автор"), new Genre("Жанр"));
        } else {
            book = bookRepository.findById(id).orElseThrow(NotFoundException::new);
        }
        return book;
    }

    @Override
    public Book save(Book book, String authorId, String genreId) {
        if (book.getId() == null || book.getId().equals("")) {
            book = new Book(book.getName(), book.getAuthor(), book.getGenre());
        }
        if (authorId != null && !authorId.isEmpty()) {
            Author author = authorRepository.findById(authorId).orElseThrow(NotFoundException::new);
            book.setAuthor(author);
        }
        if (genreId != null && !genreId.isEmpty()) {
            Genre genre = genreRepository.findById(genreId).orElseThrow(NotFoundException::new);
            book.setGenre(genre);
        }

        if (bookSaveCounter != null) {
            bookSaveCounter.increment();
        }
        return bookRepository.save(book);
    }

    @Override
    public void delete(String id) {
        bookRepository.deleteById(id);
    }

    private List<Book> getDefaultBooks() {
        return List.of(new Book("Любимая книга", new Author("Любимый автор"), new Genre("Любимый жанр")));
    }

}




