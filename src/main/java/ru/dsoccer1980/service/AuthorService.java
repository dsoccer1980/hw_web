package ru.dsoccer1980.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.Author;

public interface AuthorService {

    Flux<Author> getAll();

    Mono<Author> get(String id);

    Mono<Author> save(Author author);

    Mono<Void> delete(String id);

    Mono<Author> getById(String id);
}
