package ru.dsoccer1980.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.service.GenreService;

import java.util.List;

//@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.99.100:3000"})
@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping("/genre")
    public List<Genre> getAll() {
        return genreService.getAll();
    }

    @GetMapping("/genre/{id}")
    public Genre getById(@PathVariable("id") String id) {
        return genreService.get(id);
    }

    @PutMapping(value = "/genre", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Genre update(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    @PostMapping(value = "/genre", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Genre create(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    @DeleteMapping(value = "/genre")
    public void delete(@RequestParam("id") String id) {
        genreService.delete(id);
    }
}
