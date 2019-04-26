package ru.dsoccer1980.rest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.repository.GenreRepository;
import ru.dsoccer1980.util.exception.NotFoundException;

import java.util.List;

@Controller
public class GenreController {

    private final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @GetMapping("/genre")
    public String getAll(Model model) {
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "listGenres";
    }

    @GetMapping("/genre/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("genre", genre);
        return "editGenres";
    }

    @GetMapping("/genre/create")
    public String create(Model model) {
        model.addAttribute("genre", new Genre("Имя жанра"));
        return "editGenres";
    }

    @PostMapping(value = "/genre/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(Genre genre) {
        if (genre.getId().equals("") || genre.getId() == null) {
            genre = new Genre(genre.getName());
        }
        genreRepository.save(genre);
        return "redirect:/genre";
    }

    @PostMapping("/genre/delete")
    public String delete(@RequestParam("id") String id) {
        genreRepository.deleteById(id);
        return "redirect:/genre";
    }
}
