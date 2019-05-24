package ru.dsoccer1980.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.repository.AuthorRepository;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public Flux<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Mono<Author> get(String id) {
        return authorRepository.findById(id);
    }

    @Override
    public Mono<Author> save(Author author) {
        if (author.getId() == null || author.getId().equals("")) {
            author = new Author(author.getName());
        }
        return authorRepository.save(author);
    }

    @Override
    public Mono<Void> delete(String id) {
        return authorRepository.deleteById(id);
    }

}
