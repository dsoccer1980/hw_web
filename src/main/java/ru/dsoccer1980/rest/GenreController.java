package ru.dsoccer1980.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dsoccer1980.domain.Genre;
import ru.dsoccer1980.service.GenreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;


    @GetMapping("/genre")
    public String getAll(Model model) {
        List<Genre> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "listGenres";
    }

    @GetMapping("/genre/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        Genre genre = genreService.get(id);
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
        genreService.save(genre);
        return "redirect:/genre";
    }

    @PostMapping("/genre/delete")
    public String delete(@RequestParam("id") String id) {
        genreService.delete(id);
        return "redirect:/genre";
    }
}
