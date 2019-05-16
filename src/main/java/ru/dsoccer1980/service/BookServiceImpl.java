package ru.dsoccer1980.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.domain.Book;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.repository.AuthorRepository;
import ru.dsoccer1980.repository.BookRepository;
import ru.dsoccer1980.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    public Flux<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> get(String id) {
        return bookRepository.findById(id);
    }

    @Override
    public Mono<Book> save(Book book, String authorId, String genreId) {
        if (book.getId() == null || book.getId().equals("")) {
            book = new Book(book.getName(), book.getAuthor(), book.getGenre());
        }
        if (authorId != null && !authorId.isEmpty()) {
            Mono<Author> author = authorRepository.findById(authorId);
            //TODO
            book.setAuthor(author.block());
        }
        if (genreId != null && !genreId.isEmpty()) {
            Mono<Genre> genre = genreRepository.findById(genreId);
            //TODO
            book.setGenre(genre.block());
        }

        return bookRepository.save(book);
    }

    @Override
    public Mono<Void> delete(String id) {
        return bookRepository.deleteById(id);
    }
}




