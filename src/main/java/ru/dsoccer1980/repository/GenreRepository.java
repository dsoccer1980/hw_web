package ru.dsoccer1980.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.dsoccer1980.domain.Genre;

import java.util.Optional;


public interface GenreRepository extends ReactiveMongoRepository<Genre, String> {

    Optional<Genre> findByName(String name);

}
