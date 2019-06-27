package ru.dsoccer1980.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import ru.dsoccer1980.domain.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
