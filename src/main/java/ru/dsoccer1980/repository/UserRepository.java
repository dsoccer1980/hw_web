package ru.dsoccer1980.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.User;

public interface UserRepository extends ReactiveMongoRepository<User, Long> {

    Mono<User> findByUsername(String username);

}
