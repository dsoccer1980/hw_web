package ru.dsoccer1980.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.Genre;

public interface GenreService {

    Flux<Genre> getAll();

    Mono<Genre> get(String id);

    Mono<Genre> save(Genre genre);

    Mono<Void> delete(String id);

    Mono<Genre> getById(String id);

}
