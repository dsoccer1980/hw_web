package ru.dsoccer1980.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.repository.AuthorRepository;
import ru.dsoccer1980.util.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @HystrixCommand(groupKey = "AuthorService", commandKey = "getAll", fallbackMethod = "getDefaultAuthors")
    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @HystrixCommand(groupKey = "AuthorService", commandKey = "getById")
    @Override
    public Author get(String id) {
        return authorRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @HystrixCommand(groupKey = "AuthorService", commandKey = "save")
    @Override
    public Author save(Author author) {
        if (author.getId() == null || author.getId().equals("")) {
            author = new Author(author.getName());
        }
        return authorRepository.save(author);
    }

    @HystrixCommand(groupKey = "AuthorService", commandKey = "delete")
    @Override
    public void delete(String id) {
        authorRepository.deleteById(id);
    }

    private List<Author> getDefaultAuthors() {
        return List.of(new Author("Любимый автор"));
    }


}
