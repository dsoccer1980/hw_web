package ru.dsoccer1980.actuator;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.dsoccer1980.repository.BookRepository;


@Component
@AllArgsConstructor
public class LimitBooksHealthCheck implements HealthIndicator {

    private static final int BOOKS_COUNT = 3;
    private final BookRepository repository;

    @Override
    public Health health() {
        long count = repository.count();
        return count > BOOKS_COUNT ?
                Health.up().withDetail("Too many books", count).build() :
                Health.down().withDetail("Count of books is good", count).build();
    }
}
