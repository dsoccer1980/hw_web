package ru.dsoccer1980.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.service.GenreService;

@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre")
    public Flux<Genre> getAll() {
        return genreService.getAll();
    }

    @GetMapping("/genre/{id}")
    public Mono<Genre> getById(@PathVariable("id") String id) {
        return genreService.get(id);
    }

    @PutMapping(value = "/genre", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Genre> update(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    @PostMapping(value = "/genre", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Genre> create(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    @DeleteMapping(value = "/genre")
    public Mono<Void> delete(@RequestParam("id") String id) {
        return genreService.delete(id);
    }
}
