package ru.dsoccer1980.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.repository.GenreRepository;
import ru.dsoccer1980.util.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre get(String id) {
        return genreRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null || genre.getId().equals("")) {
            genre = new Genre(genre.getName());
        }
        return genreRepository.save(genre);
    }

    @Override
    public void delete(String id) {
        genreRepository.deleteById(id);
    }

    @Override
    public Genre getById(String id) {
        return genreRepository.findById(id).orElseThrow(NotFoundException::new);
    }

}
