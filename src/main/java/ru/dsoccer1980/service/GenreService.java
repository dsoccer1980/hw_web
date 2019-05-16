package ru.dsoccer1980.service;

import ru.dsoccer1980.domain.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAll();

    Genre get(String id);

    Genre save(Genre genre);

    void delete(String id);

    Genre getById(String id);

}
