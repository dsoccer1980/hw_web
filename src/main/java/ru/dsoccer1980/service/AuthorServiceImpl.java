package ru.dsoccer1980.service;

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

    @Override
    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author get(String id) {
        return authorRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Override
    public void save(Author author) {
        if (author.getId().equals("") || author.getId() == null) {
            author = new Author(author.getName());
        }
        authorRepository.save(author);
    }

    @Override
    public void delete(String id) {
        authorRepository.deleteById(id);
    }

}
