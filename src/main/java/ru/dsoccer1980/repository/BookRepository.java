package ru.dsoccer1980.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.dsoccer1980.domain.Book;


public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {

    Flux<Book> findByAuthorId(String id);

    Flux<Book> findByGenreId(String id);
}
