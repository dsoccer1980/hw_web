package ru.dsoccer1980.service;

import ru.dsoccer1980.domain.Author;

import java.util.List;

public interface AuthorService {

    List<Author> getAll();

    Author get(String id);

    void save(Author author);

    void delete(String id);

}
