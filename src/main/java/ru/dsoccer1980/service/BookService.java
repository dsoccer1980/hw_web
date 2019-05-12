package ru.dsoccer1980.service;

import ru.dsoccer1980.domain.Book;

import java.util.List;

public interface BookService {

    List<Book> getAll();

    Book get(String id);

    Book save(Book book, String authorId, String genreId);

    void delete(String id);
}
