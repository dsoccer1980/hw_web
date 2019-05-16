package ru.dsoccer1980.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.Book;

public interface BookService {

    Flux<Book> getAll();

    Mono<Book> get(String id);

    Mono<Book> save(Book book, String authorId, String genreId);

    Mono<Void> delete(String id);
}
