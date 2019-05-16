package ru.dsoccer1980.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.dsoccer1980.domain.Book;

import java.util.List;


public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {

    List<Book> findByAuthorId(String id);

    List<Book> findByGenreId(String id);
}
