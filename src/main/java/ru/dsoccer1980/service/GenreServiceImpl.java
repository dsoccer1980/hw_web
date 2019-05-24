package ru.dsoccer1980.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Flux<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Override
    public Mono<Genre> get(String id) {
        return genreRepository.findById(id);
    }

    @Override
    public Mono<Genre> save(Genre genre) {
        if (genre.getId() == null || genre.getId().equals("")) {
            genre = new Genre(genre.getName());
        }
        return genreRepository.save(genre);
    }

    @Override
    public Mono<Void> delete(String id) {
        return genreRepository.deleteById(id);
    }

}
