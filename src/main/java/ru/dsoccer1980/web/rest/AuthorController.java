package ru.dsoccer1980.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.service.AuthorService;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.99.100:3000"})
@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author")
    public List<Author> getAll() {
        return authorService.getAll();
    }

    @GetMapping("/author/{id}")
    public Author getById(@PathVariable("id") String id) {
        return authorService.get(id);
    }

    @PutMapping(value = "/author", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Author update(@RequestBody Author author) {
        return authorService.save(author);
    }

    @PostMapping(value = "/author", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Author create(@RequestBody Author author) {
        return authorService.save(author);
    }

    @DeleteMapping(value = "/author")
    public void delete(@RequestParam("id") String id) {
        authorService.delete(id);
    }

}
