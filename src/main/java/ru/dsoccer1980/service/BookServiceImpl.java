package ru.dsoccer1980.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

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

        return bookRepository.save(book);
    }

    @Override
    public void delete(String id) {
        bookRepository.deleteById(id);
    }
}




