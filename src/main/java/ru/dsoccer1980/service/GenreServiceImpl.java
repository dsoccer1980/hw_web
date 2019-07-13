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

    @HystrixCommand(fallbackMethod = "getDefaultGenres", groupKey = "GenreService", commandKey = "getAll")
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

    private List<Genre> getDefaultGenres() {
        return List.of(new Genre("Любимый жанр"));
    }


}
