package ru.dsoccer1980.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.service.AuthorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author")
    public String getAll(Model model) {
        List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "listAuthors";
    }

    @GetMapping("/author/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        Author author = authorService.get(id);
        model.addAttribute("author", author);
        return "editAuthors";
    }

    @GetMapping("/author/create")
    public String create(Model model) {
        model.addAttribute("author", new Author("Имя автора"));
        return "editAuthors";
    }

    @PostMapping(value = "/author/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String save(Author author) {
        authorService.save(author);
        return "redirect:/author";
    }

    @PostMapping("/author/delete")
    public String delete(@RequestParam("id") String id) {
        authorService.delete(id);
        return "redirect:/author";
    }
}
