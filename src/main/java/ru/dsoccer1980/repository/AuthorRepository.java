package ru.dsoccer1980.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.dsoccer1980.domain.Author;

import java.util.Optional;


public interface AuthorRepository extends ReactiveMongoRepository<Author, String> {

    Optional<Author> findByName(String name);

}
