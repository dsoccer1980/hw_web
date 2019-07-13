package ru.dsoccer1980.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    @HystrixCommand(groupKey = "GenreService", commandKey = "getAll", fallbackMethod = "getDefaultGenres")
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @HystrixCommand(groupKey = "GenreService", commandKey = "getById")
    @Override
    public Genre get(String id) {
        return genreRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @HystrixCommand(groupKey = "GenreService", commandKey = "save")
    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null || genre.getId().equals("")) {
            genre = new Genre(genre.getName());
        }
        return genreRepository.save(genre);
    }

    @HystrixCommand(groupKey = "GenreService", commandKey = "delete")
    @Override
    public void delete(String id) {
        genreRepository.deleteById(id);
    }

    private List<Genre> getDefaultGenres() {
        return List.of(new Genre("Любимый жанр"));
    }


}
