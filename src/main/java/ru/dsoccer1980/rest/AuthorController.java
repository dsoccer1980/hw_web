package ru.dsoccer1980.rest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dsoccer1980.domain.Author;
import ru.dsoccer1980.repository.AuthorRepository;
import ru.dsoccer1980.util.exception.NotFoundException;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping("/author")
    public String getAll(Model model) {
        List<Author> authors = authorRepository.findAll();
        model.addAttribute("authors", authors);
        return "listAuthors";
    }

    @GetMapping("/author/edit")
    public String edit(@RequestParam("id") String id, Model model) {
        Author author = authorRepository.findById(id).orElseThrow(NotFoundException::new);
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
        if (author.getId().equals("") || author.getId() == null) {
            author = new Author(author.getName());
        }
        authorRepository.save(author);
        return "redirect:/author";
    }

    @PostMapping("/author/delete")
    public String delete(@RequestParam("id") String id) {
        authorRepository.deleteById(id);
        return "redirect:/author";
    }
}
