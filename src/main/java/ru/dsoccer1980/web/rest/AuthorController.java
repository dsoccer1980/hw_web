package ru.dsoccer1980.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.service.AuthorService;

@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author")
    public Flux<Author> getAll() {
        return authorService.getAll();
    }

    @GetMapping("/author/id/{id}")
    public Mono<Author> getById(@PathVariable("id") String id) {
        return authorService.getById(id);
    }

    @GetMapping("/author/edit/{id}")
    public Mono<Author> edit(@PathVariable("id") String id) {
        return authorService.get(id);
    }

    @PutMapping(value = "/author/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Author> update(@RequestBody Author author) {
        return authorService.save(author);
    }

    @PostMapping(value = "/author/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Author> create(@RequestBody Author author) {
        return authorService.save(author);
    }

    @DeleteMapping(value = "/author/delete")
    public Mono<Void> delete(@RequestParam("id") String id) {
        return authorService.delete(id);
    }

}
